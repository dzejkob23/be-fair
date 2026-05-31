package dev.jakubzika.befair.di

import dev.jakubzika.befair.data.repository.ProfileRepositoryImpl
import dev.jakubzika.befair.domain.repository.ProfileRepository

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
}
