package dev.jakubzika.befair.routes

import dev.jakubzika.befair.auth.EmailService
import dev.jakubzika.befair.auth.JwtConfig
import dev.jakubzika.befair.auth.OtpGenerator
import dev.jakubzika.befair.auth.PasswordHasher
import dev.jakubzika.befair.data.db.UserRepository
import dev.jakubzika.befair.domain.model.GenericResponse
import dev.jakubzika.befair.domain.model.LoginRequest
import dev.jakubzika.befair.domain.model.ProfileResponse
import dev.jakubzika.befair.domain.model.RefreshRequest
import dev.jakubzika.befair.domain.model.RegisterRequest
import dev.jakubzika.befair.domain.model.TokenResponse
import dev.jakubzika.befair.domain.model.VerifyOtpRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

/**
 * Mounts all authentication endpoints under /api. The OTP registration flow is:
 * register -> (OTP printed to console) -> verify -> tokens issued.
 */
fun Route.authRoutes(userRepository: UserRepository, emailService: EmailService) {
    route("/api") {
        route("/auth") {
            register(userRepository, emailService)
            verify(userRepository)
            login(userRepository)
            refresh()
        }
        profile(userRepository)
    }
}

private fun Route.register(userRepository: UserRepository, emailService: EmailService) = post("/register") {
    val request = call.receive<RegisterRequest>()

    if (request.email.isBlank() || request.password.length < 8 || request.name.isBlank()) {
        call.respond(
            HttpStatusCode.BadRequest,
            GenericResponse(false, "Email and name are required, and password must be at least 8 characters."),
        )
        return@post
    }

    val existing = userRepository.findByEmail(request.email)
    if (existing != null && existing.isVerified) {
        call.respond(
            HttpStatusCode.Conflict,
            GenericResponse(false, "An account with this email already exists."),
        )
        return@post
    }

    val passwordHash = PasswordHasher.hash(request.password)
    if (existing == null) {
        userRepository.create(request.email, request.name, passwordHash)
    } else {
        // Unverified re-registration: refresh the stored password before re-sending an OTP.
        userRepository.updatePasswordHash(request.email, passwordHash)
    }

    val otp = OtpGenerator.generate()
    userRepository.setOtp(request.email, otp, OtpGenerator.expiresAt())
    emailService.sendOtpEmail(request.email, otp)

    call.respond(GenericResponse(true, "Verification code sent to ${request.email}."))
}

private fun Route.verify(userRepository: UserRepository) = post("/verify") {
    val request = call.receive<VerifyOtpRequest>()
    val user = userRepository.findByEmail(request.email)

    val isValid = user?.otpCode != null &&
        user.otpCode == request.otp &&
        user.otpExpiresAt != null &&
        System.currentTimeMillis() < user.otpExpiresAt

    if (user == null || !isValid) {
        call.respond(
            HttpStatusCode.Unauthorized,
            GenericResponse(false, "Invalid or expired verification code."),
        )
        return@post
    }

    userRepository.markVerifiedAndClearOtp(user.email)
    call.respond(tokensFor(user.id.toString()))
}

private fun Route.login(userRepository: UserRepository) = post("/login") {
    val request = call.receive<LoginRequest>()
    val user = userRepository.findByEmail(request.email)

    if (user == null || !PasswordHasher.verify(request.password, user.passwordHash)) {
        call.respond(
            HttpStatusCode.Unauthorized,
            GenericResponse(false, "Invalid email or password."),
        )
        return@post
    }
    if (!user.isVerified) {
        call.respond(
            HttpStatusCode.Forbidden,
            GenericResponse(false, "Please verify your email before signing in."),
        )
        return@post
    }

    call.respond(tokensFor(user.id.toString()))
}

private fun Route.refresh() = post("/refresh") {
    val request = call.receive<RefreshRequest>()
    val userId = JwtConfig.verifyRefreshToken(request.refreshToken)

    if (userId == null) {
        call.respond(
            HttpStatusCode.Unauthorized,
            GenericResponse(false, "Invalid or expired refresh token."),
        )
        return@post
    }

    // Refresh token is reused (not rotated) for simplicity.
    call.respond(
        TokenResponse(
            accessToken = JwtConfig.generateAccessToken(userId),
            refreshToken = request.refreshToken,
        ),
    )
}

private fun Route.profile(userRepository: UserRepository) = authenticate("auth-jwt") {
    get("/profile") {
        val principal = call.principal<JWTPrincipal>()
        val userId = principal?.let { JwtConfig.userIdClaim(it.payload) }
        val user = userId?.toIntOrNull()?.let { userRepository.findById(it) }

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, GenericResponse(false, "Unknown user."))
            return@get
        }
        call.respond(ProfileResponse(userId = user.id.toString(), email = user.email, displayName = user.displayName))
    }
}

private fun tokensFor(userId: String) = TokenResponse(
    accessToken = JwtConfig.generateAccessToken(userId),
    refreshToken = JwtConfig.generateRefreshToken(userId),
)
