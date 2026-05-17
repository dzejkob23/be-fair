package dev.jakubzika.befair.di

import dev.jakubzika.befair.data.network.createHttpClient
import io.ktor.client.HttpClient

/**
 * Instance of this class represents a container equivalent to dependency injection framework. It
 * keeps all relevant instances together and instantiate them when needed.
 */
class AppContainer {

    // Network client
    val httpClient: HttpClient by lazy { createHttpClient() }
}
