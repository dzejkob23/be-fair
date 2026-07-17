package dev.jakubzika.befair.auth

import at.favre.lib.crypto.bcrypt.BCrypt

/** Thin wrapper around BCrypt for hashing and verifying user passwords. */
object PasswordHasher {

    private const val COST = 12

    fun hash(password: String): String =
        BCrypt.withDefaults().hashToString(COST, password.toCharArray())

    fun verify(password: String, hash: String): Boolean =
        BCrypt.verifyer().verify(password.toCharArray(), hash).verified
}
