package dev.jakubzika.befair.domain.repository

import dev.jakubzika.befair.domain.AuthResult
import dev.jakubzika.befair.domain.model.ProfileResponse

/**
 * Client-side authentication operations. Implementations talk to the Ktor backend and
 * persist tokens securely on success.
 */
interface AuthRepository {

    /** Registers a new account with a display name and triggers an OTP email. Does not log the user in. */
    suspend fun register(name: String, email: String, password: String): AuthResult<Unit>

    /** Verifies the emailed OTP; on success persists the returned tokens and caches the profile. */
    suspend fun verifyOtp(email: String, otp: String): AuthResult<Unit>

    /** Logs in a verified user; on success persists the returned tokens. */
    suspend fun login(email: String, password: String): AuthResult<Unit>

    /** Calls the protected profile endpoint (proves Bearer injection). */
    suspend fun fetchProfile(): AuthResult<ProfileResponse>

    /** Clears persisted tokens and profile data. */
    fun logout()

    /** Whether a token pair is currently stored. */
    fun isLoggedIn(): Boolean
}
