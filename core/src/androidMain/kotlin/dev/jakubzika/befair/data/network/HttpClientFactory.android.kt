package dev.jakubzika.befair.data.network

import dev.jakubzika.befair.data.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun createHttpClient(tokenStorage: TokenStorage): HttpClient = HttpClient(OkHttp) {
    configureBeFair(tokenStorage)
}
