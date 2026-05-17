package dev.jakubzika.befair.domain.repository

import dev.jakubzika.befair.domain.model.UserProfile
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val profile: StateFlow<UserProfile?>
    suspend fun refreshProfile(userId: String)
}
