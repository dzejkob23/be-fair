package dev.jakubzika.befair

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            Scaffold {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Hello World!"
                    )
                }
            }
        }
    }
}