package dev.jakubzika.befair.di

import dev.jakubzika.befair.data.repository.ProfileRepositoryImpl
import dev.jakubzika.befair.domain.repository.ProfileRepository
import dev.jakubzika.befair.domain.usecase.GetProfileUseCase

/**
 * Mobile-specific dependency container. Holds mobile platform use-cases, repositories,
 * and controllers on top of the shared [AppContainer].
 */
class MobileAppContainer(
    val appContainer: AppContainer = AppContainer()
) {
    val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(appContainer.httpClient)
    }
    val getProfileUseCase: GetProfileUseCase get() = GetProfileUseCase(profileRepository)
}
