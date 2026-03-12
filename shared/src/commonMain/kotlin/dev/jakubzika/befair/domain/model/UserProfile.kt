package dev.jakubzika.befair.domain.model

data class UserProfile(
    val id: String,
    val displayName: String,
    val email: String,
    val avatarUrl: String? = null,
)
