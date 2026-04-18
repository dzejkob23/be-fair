package dev.jakubzika.befair.di

/**
 * Mobile-specific dependency container. Holds mobile platform use-cases, repositories,
 * and controllers on top of the shared [AppContainer].
 */
class MobileAppContainer(
    val appContainer: AppContainer = AppContainer()
)
