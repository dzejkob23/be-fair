package dev.jakubzika.befair.data.db

import org.jetbrains.exposed.v1.core.Table

/**
 * Exposed table definition for application users.
 *
 * OTP fields are nullable because they are only populated between registration and
 * successful verification, then cleared.
 */
object Users : Table("users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 100)
    val displayName = varchar("display_name", 255)
    val isVerified = bool("is_verified").default(false)
    val otpCode = varchar("otp_code", 6).nullable()
    val otpExpiresAt = long("otp_expires_at").nullable()

    override val primaryKey = PrimaryKey(id)
}

/** Plain row carrier so query results can leave a transaction safely. */
data class UserRow(
    val id: Int,
    val email: String,
    val passwordHash: String,
    val displayName: String,
    val isVerified: Boolean,
    val otpCode: String?,
    val otpExpiresAt: Long?,
)
