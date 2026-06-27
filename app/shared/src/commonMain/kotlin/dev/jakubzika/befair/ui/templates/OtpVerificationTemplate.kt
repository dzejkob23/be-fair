package dev.jakubzika.befair.ui.templates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import dev.jakubzika.befair.ui.atoms.BeFairDimension
import dev.jakubzika.befair.ui.atoms.BeFairTextField
import dev.jakubzika.befair.ui.atoms.BeFairTheme
import dev.jakubzika.befair.ui.atoms.BrandMark
import dev.jakubzika.befair.ui.atoms.PrimaryButton
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.otp_field_label
import be_fair.app.shared.generated.resources.otp_field_placeholder
import be_fair.app.shared.generated.resources.otp_submit
import be_fair.app.shared.generated.resources.otp_subtitle
import be_fair.app.shared.generated.resources.otp_title
import org.jetbrains.compose.resources.stringResource

private const val OTP_LENGTH = 6

@Composable
fun OtpVerificationTemplate(
    email: String,
    otp: String,
    onOtpChange: (String) -> Unit,
    onSubmit: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(BeFairDimension.Spacing.md),
    ) {
        BrandMark()

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.xl))

        Text(
            text = stringResource(Res.string.otp_title),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.xs))

        Text(
            text = stringResource(Res.string.otp_subtitle, email),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.lg))

        BeFairTextField(
            value = otp,
            // Keep only digits, capped at the OTP length.
            onValueChange = { onOtpChange(it.filter(Char::isDigit).take(OTP_LENGTH)) },
            label = stringResource(Res.string.otp_field_label),
            placeholder = stringResource(Res.string.otp_field_placeholder),
            isEnabled = !isLoading,
            isError = errorMessage != null,
            errorMessage = errorMessage,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.lg))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.otp_submit),
            isEnabled = !isLoading && otp.length == OTP_LENGTH,
            onClick = onSubmit,
        )

        if (isLoading) {
            Spacer(modifier = Modifier.height(BeFairDimension.Spacing.md))
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun OtpVerificationTemplatePreview() {
    BeFairTheme {
        OtpVerificationTemplate(
            email = "name@example.com",
            otp = "123",
            onOtpChange = {},
            onSubmit = {},
        )
    }
}
