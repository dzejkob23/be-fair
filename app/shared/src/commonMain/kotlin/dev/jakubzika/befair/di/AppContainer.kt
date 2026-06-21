package dev.jakubzika.befair.di

import dev.jakubzika.befair.data.repository.ProfileRepositoryImpl
import dev.jakubzika.befair.domain.repository.ProfileRepository

/**
 * Mobile-specific dependency container. Holds mobile platform use-cases, repositories,
 * and controllers on top of the shared [CoreContainer].
 */
class AppContainer(
    val coreContainer: CoreContainer = CoreContainer()
) {
    val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(coreContainer.httpClient)
    }
}
