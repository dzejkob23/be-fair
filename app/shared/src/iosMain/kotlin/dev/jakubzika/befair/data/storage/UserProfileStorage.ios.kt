package dev.jakubzika.befair.data.storage

import platform.Foundation.NSUserDefaults

actual class UserProfileStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    actual fun saveProfile(displayName: String, email: String) {
        defaults.setObject(displayName, forKey = PROFILE_KEY_DISPLAY_NAME)
        defaults.setObject(email, forKey = PROFILE_KEY_EMAIL)
    }

    actual fun getDisplayName(): String? =
        defaults.stringForKey(PROFILE_KEY_DISPLAY_NAME)

    actual fun getEmail(): String? =
        defaults.stringForKey(PROFILE_KEY_EMAIL)

    actual fun clearProfile() {
        defaults.removeObjectForKey(PROFILE_KEY_DISPLAY_NAME)
        defaults.removeObjectForKey(PROFILE_KEY_EMAIL)
    }
}


