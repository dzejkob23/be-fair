package dev.jakubzika.befair

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.jakubzika.befair.di.MobileAppContainer
import dev.jakubzika.befair.ui.LocalAppContainer
import dev.jakubzika.befair.ui.atoms.BeFairTheme
import dev.jakubzika.befair.ui.navigation.AddNewItem
import dev.jakubzika.befair.ui.navigation.CreateAccount
import dev.jakubzika.befair.ui.navigation.ItemDetail
import dev.jakubzika.befair.ui.navigation.Items
import dev.jakubzika.befair.ui.navigation.Main
import dev.jakubzika.befair.ui.navigation.Overview
import dev.jakubzika.befair.ui.navigation.Profile
import dev.jakubzika.befair.ui.navigation.Route
import dev.jakubzika.befair.ui.navigation.SignIn
import dev.jakubzika.befair.ui.screens.CreateAccountScreen
import dev.jakubzika.befair.ui.screens.MainScreen
import dev.jakubzika.befair.ui.screens.SignInScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.serializer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // DI container initialization
    val appContainer = remember { MobileAppContainer() }
    CompositionLocalProvider(LocalAppContainer provides appContainer) {
        // General app theme
        BeFairTheme {
            val config = remember {
                SavedStateConfiguration {
                    serializersModule = SerializersModule {
                        polymorphic(Route::class) {
                            subclass(SignIn::class, serializer())
                            subclass(CreateAccount::class, serializer())
                            subclass(Main::class, serializer())
                            // Also include nested routes for serialization support if needed at top level
                            subclass(Overview::class, serializer())
                            subclass(Items::class, serializer())
                            subclass(Profile::class, serializer())
                            subclass(ItemDetail::class, serializer())
                            subclass(AddNewItem::class, serializer())
                        }
                    }
                }
            }
            @Suppress("UNCHECKED_CAST")
            val backStack: NavBackStack<Route> = rememberNavBackStack(config, SignIn) as NavBackStack<Route>

            NavDisplay(
                modifier = Modifier.fillMaxSize(),
                backStack = backStack,
                onBack = { backStack.removeLast() },
                entryProvider = entryProvider {
                    entry<SignIn> {
                        SignInScreen(
                            onSignIn = { backStack.add(Main) },
                            onNavToCreateAccount = { backStack.add(CreateAccount) }
                        )
                    }
                    entry<CreateAccount> {
                        CreateAccountScreen(
                            onCreateAccount = { backStack.add(Main) },
                            onNavToSignIn = { backStack.removeLast() }
                        )
                    }
                    entry<Main> {
                        MainScreen(
                            onSignOut = {
                                // Clear backstack and go to SignIn
                                while (backStack.size > 0) {
                                    backStack.removeLast()
                                }
                                backStack.add(SignIn)
                            }
                        )
                    }
                }
            )
        }
    }
}
