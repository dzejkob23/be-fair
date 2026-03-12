package dev.jakubzika.befair.data.repository

import dev.jakubzika.befair.domain.model.UserProfile
import dev.jakubzika.befair.domain.repository.ProfileRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileRepositoryImpl(private val httpClient: HttpClient) : ProfileRepository {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    override val profile: StateFlow<UserProfile?> = _profile.asStateFlow()

    override suspend fun refreshProfile(userId: String) {
        try {
            httpClient.get("https://befair.jakubzika.dev/api/profile/$userId")
            // TODO: deserialize real response once the server endpoint exists
        } catch (_: Exception) {
            // No server endpoint yet — fall back to dummy data
        }
        _profile.value = UserProfile(
            id = userId,
            displayName = "Jane Doe",
            email = "jane@example.com",
            avatarUrl = null,
        )
    }
}
