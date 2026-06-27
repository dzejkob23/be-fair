package dev.jakubzika.befair.data.network

import dev.jakubzika.befair.data.storage.TokenStorage
import io.ktor.client.HttpClient

expect fun createHttpClient(tokenStorage: TokenStorage): HttpClient

