package dev.jakubzika.befair

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.jakubzika.befair.di.MobileAppContainer
import dev.jakubzika.befair.ui.LocalAppContainer
import dev.jakubzika.befair.ui.atoms.BeFairTheme
import dev.jakubzika.befair.ui.navigation.Home
import dev.jakubzika.befair.ui.navigation.Route
import dev.jakubzika.befair.ui.navigation.Settings
import dev.jakubzika.befair.ui.screens.HomeScreen
import dev.jakubzika.befair.ui.screens.SettingsScreen
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
                            subclass(Home::class, serializer())
                            subclass(Settings::class, serializer())
                        }
                    }
                }
            }
            val backStack = rememberNavBackStack(config, Home)

            Scaffold { innerPadding ->
                NavDisplay(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    backStack = backStack,
                    onBack = { backStack.removeLast() },
                    entryProvider = entryProvider {
                        entry<Home> {
                            HomeScreen(
                                onNavigateToSettings = { backStack.add(Settings) }
                            )
                        }
                        entry<Settings> {
                            SettingsScreen(
                                onBack = { backStack.removeLast() }
                            )
                        }
                    }
                )
            }
        }
    }
}
