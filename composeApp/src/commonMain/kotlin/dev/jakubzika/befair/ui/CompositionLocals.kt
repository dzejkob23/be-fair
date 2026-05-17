package dev.jakubzika.befair.ui

import androidx.compose.runtime.staticCompositionLocalOf
import dev.jakubzika.befair.di.MobileAppContainer

val LocalAppContainer = staticCompositionLocalOf<MobileAppContainer> {
    error("No AppContainer provided")
}
