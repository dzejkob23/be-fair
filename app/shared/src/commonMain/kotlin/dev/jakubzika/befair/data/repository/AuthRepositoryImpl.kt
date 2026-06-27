package dev.jakubzika.befair.data.repository

import dev.jakubzika.befair.data.network.API_BASE_URL
import dev.jakubzika.befair.data.storage.TokenStorage
import dev.jakubzika.befair.domain.AuthResult
import dev.jakubzika.befair.domain.model.GenericResponse
import dev.jakubzika.befair.domain.model.LoginRequest
import dev.jakubzika.befair.domain.model.ProfileResponse
import dev.jakubzika.befair.domain.model.RegisterRequest
import dev.jakubzika.befair.domain.model.TokenResponse
import dev.jakubzika.befair.domain.model.VerifyOtpRequest
import dev.jakubzika.befair.domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage,
) : AuthRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun register(email: String, password: String): AuthResult<Unit> = safeCall {
        val response: GenericResponse = client.post("$API_BASE_URL/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest(email, password))
        }.body()
        check(response.success) { response.message }
    }

    override suspend fun verifyOtp(email: String, otp: String): AuthResult<Unit> = safeCall {
        val tokens: TokenResponse = client.post("$API_BASE_URL/api/auth/verify") {
            contentType(ContentType.Application.Json)
            setBody(VerifyOtpRequest(email, otp))
        }.body()
        tokenStorage.saveTokens(tokens.accessToken, tokens.refreshToken)
    }

    override suspend fun login(email: String, password: String): AuthResult<Unit> = safeCall {
        val tokens: TokenResponse = client.post("$API_BASE_URL/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }.body()
        tokenStorage.saveTokens(tokens.accessToken, tokens.refreshToken)
    }

    override suspend fun fetchProfile(): AuthResult<ProfileResponse> = safeCall {
        client.get("$API_BASE_URL/api/profile").body()
    }

    override fun logout() = tokenStorage.clearTokens()

    override fun isLoggedIn(): Boolean =
        tokenStorage.getAccessToken() != null && tokenStorage.getRefreshToken() != null

    private suspend fun <T> safeCall(block: suspend () -> T): AuthResult<T> = try {
        AuthResult.Success(block())
    } catch (e: ResponseException) {
        AuthResult.Error(parseError(e))
    } catch (e: Exception) {
        AuthResult.Error(e.message ?: "Network error. Please try again.")
    }

    /** Best-effort extraction of the server's GenericResponse.message from an error body. */
    private suspend fun parseError(e: ResponseException): String = try {
        json.decodeFromString<GenericResponse>(e.response.bodyAsText()).message
    } catch (_: Exception) {
        "Request failed (${e.response.status.value})."
    }
}
