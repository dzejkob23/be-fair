package dev.jakubzika.befair.ui.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import dev.jakubzika.befair.ui.atoms.BeFairDimension
import dev.jakubzika.befair.ui.atoms.BeFairTextField
import dev.jakubzika.befair.ui.atoms.BrandMark
import dev.jakubzika.befair.ui.atoms.EmailTextField
import dev.jakubzika.befair.ui.atoms.LinkButton
import dev.jakubzika.befair.ui.atoms.LocalBeFairExtendedColors
import dev.jakubzika.befair.ui.atoms.PasswordTextField
import dev.jakubzika.befair.ui.atoms.PrimaryButton
import dev.jakubzika.befair.ui.atoms.SecondaryButton

enum class AuthMode { SignIn, Register }

private val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthTemplate(
    onSubmit: (name: String?, email: String) -> Unit,
    onContinueWithGoogle: () -> Unit,
    onContinueWithApple: () -> Unit,
    modifier: Modifier = Modifier
) {
    var mode by remember { mutableStateOf(AuthMode.SignIn) }

    var name by remember(mode) { mutableStateOf("") }
    var email by remember(mode) { mutableStateOf("") }
    var password by remember(mode) { mutableStateOf("") }
    var nameError by remember(mode) { mutableStateOf<String?>(null) }
    var emailError by remember(mode) { mutableStateOf<String?>(null) }
    var passwordError by remember(mode) { mutableStateOf<String?>(null) }

    fun validateAndSubmit() {
        val isRegister = mode == AuthMode.Register
        val nextNameError = if (isRegister && name.isBlank()) "Enter your name." else null
        val nextEmailError = if (!emailRegex.matches(email)) "Enter a valid email address." else null
        val nextPasswordError = if (password.length < 8) "Minimum 8 characters." else null

        nameError = nextNameError
        emailError = nextEmailError
        passwordError = nextPasswordError

        if (nextNameError == null && nextEmailError == null && nextPasswordError == null) {
            onSubmit(if (isRegister) name.trim() else null, email)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(BeFairDimension.Spacing.md)
    ) {
        BrandMark()

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.xl))

        Text(
            text = if (mode == AuthMode.SignIn) "Sign in" else "Create account",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.xs))

        Text(
            text = if (mode == AuthMode.SignIn) {
                "Welcome back. Sign in to see the true cost of what you own."
            } else {
                "Track what your clothes and tools really cost."
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.lg))

        if (mode == AuthMode.Register) {
            BeFairTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                placeholder = "Your name",
                isError = nameError != null,
                errorMessage = nameError,
                autofillContentType = ContentType.PersonFullName
            )
            Spacer(modifier = Modifier.height(BeFairDimension.Spacing.sm))
        }

        EmailTextField(
            value = email,
            onValueChange = { email = it },
            isError = emailError != null,
            errorMessage = emailError
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.sm))

        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = if (mode == AuthMode.Register) "At least 8 characters" else "Your password",
            isError = passwordError != null,
            errorMessage = passwordError,
            autofillContentType = if (mode == AuthMode.Register) {
                ContentType.NewPassword
            } else {
                ContentType.Password
            }
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.lg))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            title = if (mode == AuthMode.SignIn) "Sign in" else "Create account",
            onClick = ::validateAndSubmit
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.lg))

        AuthDivider()

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.lg))

        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            title = "Continue with Google",
            onClick = onContinueWithGoogle
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.sm))

        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            title = "Continue with Apple",
            onClick = onContinueWithApple
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.xl))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(BeFairDimension.Spacing.xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (mode == AuthMode.SignIn) {
                    "Don't have an account?"
                } else {
                    "Already have an account?"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LinkButton(
                text = if (mode == AuthMode.SignIn) "Create one" else "Sign in",
                onClick = {
                    mode = if (mode == AuthMode.SignIn) AuthMode.Register else AuthMode.SignIn
                }
            )
        }
    }
}

// "or" divider per DESIGN.md ".w-auth__divider": hairline rule with centered
// "or" in ink-3. Single-use, kept local to this template.
@Composable
private fun AuthDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Box(
            modifier = Modifier.padding(horizontal = BeFairDimension.Spacing.sm)
        ) {
            Text(
                text = "or",
                style = MaterialTheme.typography.bodySmall,
                color = LocalBeFairExtendedColors.current.ink3
            )
        }
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}
