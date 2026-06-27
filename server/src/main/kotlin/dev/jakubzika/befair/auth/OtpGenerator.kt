package dev.jakubzika.befair.auth

import kotlin.random.Random

/** Generates 6-digit numeric one-time codes and their expiry timestamps. */
object OtpGenerator {

    private const val VALIDITY_MILLIS = 10 * 60 * 1000L // 10 minutes

    /** Returns a 6-digit numeric string in the inclusive range 100000..999999. */
    fun generate(): String = Random.nextInt(100000, 1000000).toString()

    /** Expiry timestamp (epoch millis) for a code generated now. */
    fun expiresAt(nowMillis: Long = System.currentTimeMillis()): Long =
        nowMillis + VALIDITY_MILLIS
}
