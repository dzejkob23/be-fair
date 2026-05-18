package dev.jakubzika.befair

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import dev.jakubzika.befair.di.MobileAppContainer
import dev.jakubzika.befair.ui.LocalAppContainer
import dev.jakubzika.befair.ui.atoms.BeFairTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // DI container initialization
    val appContainer = remember { MobileAppContainer() }
    CompositionLocalProvider(LocalAppContainer provides appContainer) {
        // General app theme
        BeFairTheme {
            // TBD
        }
    }
}