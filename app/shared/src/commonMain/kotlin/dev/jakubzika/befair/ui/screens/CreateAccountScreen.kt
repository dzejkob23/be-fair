package dev.jakubzika.befair.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.jakubzika.befair.ui.atoms.PrimaryButton

@Composable
fun CreateAccountScreen(
    onCreateAccount: () -> Unit,
    onNavToSignIn: () -> Unit
) {
    var nickname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordAgain by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Create Account Screen")
            PrimaryButton(title = "Create Account", onClick = onCreateAccount)
            PrimaryButton(title = "Sign In", onClick = onNavToSignIn)
        }
    }
}
