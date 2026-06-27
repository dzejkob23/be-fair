package dev.jakubzika.befair.di

import dev.jakubzika.befair.data.network.createHttpClient
import dev.jakubzika.befair.data.repository.AuthRepositoryImpl
import dev.jakubzika.befair.data.storage.TokenStorage
import dev.jakubzika.befair.domain.repository.AuthRepository
import io.ktor.client.HttpClient

/**
 * Instance of this class represents a container equivalent to dependency injection framework. It
 * keeps all relevant instances together and instantiate them when needed.
 */
class CoreContainer {

    // Secure token storage (singleton — survives config changes)
    val tokenStorage: TokenStorage by lazy { TokenStorage() }

    // Network client, wired with bearer auth backed by [tokenStorage]
    val httpClient: HttpClient by lazy { createHttpClient(tokenStorage) }

    // Authentication repository
    val authRepository: AuthRepository by lazy { AuthRepositoryImpl(httpClient, tokenStorage) }
}
