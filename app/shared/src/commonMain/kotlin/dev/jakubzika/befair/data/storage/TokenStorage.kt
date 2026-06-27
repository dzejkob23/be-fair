package dev.jakubzika.befair.data.storage

/**
 * Secure, platform-backed persistence for the JWT access/refresh token pair.
 *
 * Actual implementations use the OS secure store — never plaintext preferences:
 *  - Android: EncryptedSharedPreferences
 *  - iOS: Keychain (`kSecClassGenericPassword`)
 */
expect class TokenStorage() {
    fun saveTokens(access: String, refresh: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearTokens()
}

internal const val TOKEN_KEY_ACCESS = "befair_access_token"
internal const val TOKEN_KEY_REFRESH = "befair_refresh_token"
