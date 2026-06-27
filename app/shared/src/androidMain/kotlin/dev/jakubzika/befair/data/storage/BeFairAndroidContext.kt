package dev.jakubzika.befair.data.storage

import android.content.Context

/**
 * Holds the application [Context] needed by Android-only components such as
 * EncryptedSharedPreferences. Initialized once from the Android entry point
 * (MainActivity/Application) before any [TokenStorage] is constructed.
 */
object BeFairAndroidContext {

    @Volatile
    lateinit var application: Context
        private set

    fun init(context: Context) {
        if (!::application.isInitialized) {
            application = context.applicationContext
        }
    }
}
