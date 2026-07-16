package dev.jakubzika.befair.data.storage

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Android [UserProfileStorage] backed by EncryptedSharedPreferences (AES-256). Requires the
 * application context to have been provided via [BeFairAndroidContext.init].
 */
actual class UserProfileStorage {

    private val prefs: SharedPreferences by lazy {
        val context = BeFairAndroidContext.application
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "befair_profile_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    actual fun saveProfile(displayName: String, email: String) {
        prefs.edit()
            .putString(PROFILE_KEY_DISPLAY_NAME, displayName)
            .putString(PROFILE_KEY_EMAIL, email)
            .apply()
    }

    actual fun getDisplayName(): String? =
        prefs.getString(PROFILE_KEY_DISPLAY_NAME, null)

    actual fun getEmail(): String? =
        prefs.getString(PROFILE_KEY_EMAIL, null)

    actual fun clearProfile() {
        prefs.edit()
            .remove(PROFILE_KEY_DISPLAY_NAME)
            .remove(PROFILE_KEY_EMAIL)
            .apply()
    }
}


