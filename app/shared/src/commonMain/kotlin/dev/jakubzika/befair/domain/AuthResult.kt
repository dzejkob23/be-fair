package dev.jakubzika.befair.domain

/**
 * Minimal result wrapper for auth network calls so the UI can branch on success/failure
 * without dealing with exceptions directly.
 */
sealed interface AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>
    data class Error(val message: String) : AuthResult<Nothing>
}
