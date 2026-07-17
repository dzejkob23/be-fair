package dev.jakubzika.befair.di

import dev.jakubzika.befair.data.network.createHttpClient
import io.ktor.client.HttpClient

/**
 * Instance of this class represents a container equivalent to dependency injection framework. It
 * keeps all relevant instances together and instantiate them when needed.
 *
 * Only genuinely cross-platform (Android + iOS + JVM/server) dependencies belong here. Mobile-only
 * wiring (auth, secure token storage) lives in `app/shared`'s AppContainer.
 */
class CoreContainer {

    // Shared, unconfigured network client. Consumers install their own plugins via createHttpClient { }.
    val httpClient: HttpClient by lazy { createHttpClient() }
}
