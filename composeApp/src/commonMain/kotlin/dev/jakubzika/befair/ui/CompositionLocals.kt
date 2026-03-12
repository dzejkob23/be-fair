package dev.jakubzika.befair.ui

import androidx.compose.runtime.staticCompositionLocalOf
import dev.jakubzika.befair.di.AppContainer

val LocalAppContainer = staticCompositionLocalOf<AppContainer> {
    error("No AppContainer provided")
}
