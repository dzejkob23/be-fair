package dev.jakubzika.befair.data.network

/**
 * Base URL of the Ktor backend. Differs per platform because of how each reaches a server
 * running on the developer machine:
 *  - Android emulator: `10.0.2.2` is the host loopback alias.
 *  - iOS simulator / JVM: `localhost` reaches the host directly.
 *
 * Flip these to your LAN IP (e.g. `http://192.168.x.x:8080`) when testing on a physical device.
 */
expect val API_BASE_URL: String
