package dev.jakubzika.befair.data.storage

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Android [TokenStorage] backed by EncryptedSharedPreferences (AES-256). Requires the
 * application context to have been provided via [BeFairAndroidContext.init].
 */
actual class TokenStorage actual constructor() {

    private val prefs: SharedPreferences by lazy {
        val context = BeFairAndroidContext.application
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "befair_secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    actual fun saveTokens(access: String, refresh: String) {
        prefs.edit()
            .putString(TOKEN_KEY_ACCESS, access)
            .putString(TOKEN_KEY_REFRESH, refresh)
            .apply()
    }

    actual fun getAccessToken(): String? = prefs.getString(TOKEN_KEY_ACCESS, null)

    actual fun getRefreshToken(): String? = prefs.getString(TOKEN_KEY_REFRESH, null)

    actual fun clearTokens() {
        prefs.edit()
            .remove(TOKEN_KEY_ACCESS)
            .remove(TOKEN_KEY_REFRESH)
            .apply()
    }
}
