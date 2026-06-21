package dev.jakubzika.befair.ui.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Input specs per DESIGN.md "Components > Inputs": 48dp tall, 4dp radius,
// strong-hairline border that goes ink on focus and orange in the error
// state, with an orange helper message below. The label above the field is
// "label-caps" (labelLarge), uppercased at the call site.
private val TextFieldShape = RoundedCornerShape(BeFairDimension.Radius.small)
private val TextFieldHeight = 48.dp
private val TextFieldContentPadding = PaddingValues(horizontal = 14.dp)

@Composable
fun BeFairTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor = when {
        isError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.outline
    }

    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.xs))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(TextFieldHeight)
                .background(MaterialTheme.colorScheme.surface, TextFieldShape)
                .border(1.dp, borderColor, TextFieldShape)
                .onFocusChanged { isFocused = it.isFocused },
            enabled = isEnabled,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            singleLine = singleLine,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(TextFieldContentPadding),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    innerTextField()
                }
            }
        )
        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(BeFairDimension.Spacing.xs))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Email",
    placeholder: String = "name@example.com",
    isEnabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    BeFairTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        isEnabled = isEnabled,
        isError = isError,
        errorMessage = errorMessage,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            capitalization = KeyboardCapitalization.None
        )
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Password",
    placeholder: String = "Your password",
    isEnabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    BeFairTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        isEnabled = isEnabled,
        isError = isError,
        errorMessage = errorMessage,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun EmailTextFieldPreview() {
    BeFairTheme {
        EmailTextField(value = "", onValueChange = {})
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun PasswordTextFieldPreview() {
    BeFairTheme {
        PasswordTextField(value = "", onValueChange = {})
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun TextFieldFilledPreview() {
    BeFairTheme {
        BeFairTextField(
            value = "Test",
            onValueChange = {},
            label = "Name",
            placeholder = "e.g. Navy chinos"
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun TextFieldErrorPreview() {
    BeFairTheme {
        BeFairTextField(
            value = "",
            onValueChange = {},
            label = "Email",
            placeholder = "name@example.com",
            isError = true,
            errorMessage = "Enter a valid email address"
        )
    }
}
