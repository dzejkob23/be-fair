package dev.jakubzika.befair.ui.screens

import androidx.compose.runtime.Composable
import dev.jakubzika.befair.ui.templates.AuthTemplate

@Composable
fun AuthScreen(onAuthenticated: () -> Unit) {
    AuthTemplate(
        onSubmit = { _, _ -> onAuthenticated() },
        onContinueWithGoogle = onAuthenticated,
        onContinueWithApple = onAuthenticated
    )
}
