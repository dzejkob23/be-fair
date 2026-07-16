package dev.jakubzika.befair.data.storage

/**
 * Persistent, platform-backed storage for the user's profile information.
 *
 * Actual implementations use the OS preference store — never plaintext:
 *  - Android: EncryptedSharedPreferences
 *  - iOS: UserDefaults (or Keychain for sensitive data)
 *  - JVM: Preferences
 */
expect class UserProfileStorage() {
    fun saveProfile(displayName: String, email: String)
    fun getDisplayName(): String?
    fun getEmail(): String?
    fun clearProfile()
}

internal const val PROFILE_KEY_DISPLAY_NAME = "befair_display_name"
internal const val PROFILE_KEY_EMAIL = "befair_email"

