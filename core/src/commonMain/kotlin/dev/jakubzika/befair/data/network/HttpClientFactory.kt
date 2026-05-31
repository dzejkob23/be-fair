package dev.jakubzika.befair.data.network

import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient

