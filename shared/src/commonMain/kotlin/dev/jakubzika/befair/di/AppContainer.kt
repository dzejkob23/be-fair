package dev.jakubzika.befair.di

import dev.jakubzika.befair.data.network.createHttpClient
import dev.jakubzika.befair.data.repository.ProfileRepositoryImpl
import dev.jakubzika.befair.domain.repository.ProfileRepository
import dev.jakubzika.befair.domain.usecase.GetProfileUseCase
import io.ktor.client.HttpClient

/**
 * Instance of this class represents a container equivalent to dependency injection framework. It
 * keeps all relevant instances together and instantiate them when needed.
 */
class AppContainer {

    // Network client
    val httpClient: HttpClient by lazy { createHttpClient() }

    /*********************************************/
    /************** Repositories *****************/
    /*********************************************/
    val profileRepository: ProfileRepository by lazy { ProfileRepositoryImpl(httpClient) }


    /*********************************************/
    /**************** Use Cases ******************/
    /*********************************************/
    val getProfileUseCase: GetProfileUseCase get() = GetProfileUseCase(profileRepository)
}
