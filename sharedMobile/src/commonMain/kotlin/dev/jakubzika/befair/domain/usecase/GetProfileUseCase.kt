package dev.jakubzika.befair.domain.usecase

import dev.jakubzika.befair.domain.model.UserProfile
import dev.jakubzika.befair.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.StateFlow

class GetProfileUseCase(private val repository: ProfileRepository) {
    val profile: StateFlow<UserProfile?> = repository.profile
    suspend fun refresh(userId: String) = repository.refreshProfile(userId)
}
