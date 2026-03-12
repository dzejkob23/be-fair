package dev.jakubzika.befair.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.jakubzika.befair.ui.LocalAppContainer
import dev.jakubzika.befair.ui.templates.ProfileState
import dev.jakubzika.befair.ui.templates.ProfileTemplate

@Composable
fun ProfileScreen(userId: String = "user-1") {
    val useCase = LocalAppContainer.current.getProfileUseCase
    val profile by useCase.profile.collectAsStateWithLifecycle()

    LaunchedEffect(userId) {
        try {
            useCase.refresh(userId)
        } catch (e: Exception) {
            // Error handling will be refined when real API is available
        }
    }

    val state = ProfileState(
        isLoading = profile == null,
        profile = profile,
    )
    ProfileTemplate(state)
}
