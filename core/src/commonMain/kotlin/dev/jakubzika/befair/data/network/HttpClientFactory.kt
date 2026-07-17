package dev.jakubzika.befair.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

/**
 * Creates the platform [HttpClient] (engine selection is the only platform-specific part).
 * Callers pass [block] to install their own plugins (e.g. ContentNegotiation, Auth) — those
 * concerns are not shared, so they live in the consuming module, not here.
 */
expect fun createHttpClient(block: HttpClientConfig<*>.() -> Unit = {}): HttpClient
