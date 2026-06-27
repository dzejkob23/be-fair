package dev.jakubzika.befair.data.network

import dev.jakubzika.befair.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun createHttpClient(tokenStorage: TokenStorage): HttpClient = HttpClient(Darwin) {
    configureBeFair(tokenStorage)
}
