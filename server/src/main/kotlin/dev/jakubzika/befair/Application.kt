package dev.jakubzika.befair

import dev.jakubzika.befair.auth.EmailService
import dev.jakubzika.befair.auth.JwtConfig
import dev.jakubzika.befair.data.db.DatabaseFactory
import dev.jakubzika.befair.data.db.UserRepository
import dev.jakubzika.befair.domain.model.GenericResponse
import dev.jakubzika.befair.routes.authRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module(emailService: EmailService? = null) {
    DatabaseFactory.init()
    val userRepository = UserRepository()
    val apiKey = System.getenv("RESEND_API_KEY") ?: "re_dummy_api_key_for_testing"
    val actualEmailService = emailService ?: EmailService(apiKey = apiKey)

    install(ContentNegotiation) {
        json()
    }

    install(Authentication) {
        jwt("auth-jwt") {
            realm = JwtConfig.REALM
            verifier(JwtConfig.verifier)
            validate { credential ->
                if (JwtConfig.userIdClaim(credential.payload) != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                GenericResponse(false, cause.message ?: "Unexpected server error."),
            )
        }
    }

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        authRoutes(userRepository, actualEmailService)
    }
}
