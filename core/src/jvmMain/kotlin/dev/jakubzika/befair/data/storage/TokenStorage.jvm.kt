package dev.jakubzika.befair.data.storage

import java.util.concurrent.ConcurrentHashMap

/**
 * JVM [TokenStorage] actual. The JVM target exists mainly so the server can share :core;
 * there is no desktop client, so a simple in-memory store is sufficient here.
 */
actual class TokenStorage actual constructor() {

    private val store = ConcurrentHashMap<String, String>()

    actual fun saveTokens(access: String, refresh: String) {
        store[TOKEN_KEY_ACCESS] = access
        store[TOKEN_KEY_REFRESH] = refresh
    }

    actual fun getAccessToken(): String? = store[TOKEN_KEY_ACCESS]

    actual fun getRefreshToken(): String? = store[TOKEN_KEY_REFRESH]

    actual fun clearTokens() {
        store.clear()
    }
}
