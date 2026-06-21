package dev.jakubzika.befair.ui.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Button specs per DESIGN.md "Components > Buttons": 48dp tall, 4dp radius
// (rounded.md), 24dp horizontal padding, Bold 15sp label (label-action /
// titleLarge). The system is functionally flat, so no elevation/shadow is
// applied to buttons.
private val ButtonShape = RoundedCornerShape(BeFairDimension.Radius.small)
private val ButtonHeight = 48.dp
private val ButtonContentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)

@Composable
private fun flatElevation() = ButtonDefaults.buttonElevation(
    defaultElevation = 0.dp,
    pressedElevation = 0.dp,
    disabledElevation = 0.dp
)

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    title: String,
    isEnabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = onClick ?: {},
        modifier = modifier.heightIn(min = ButtonHeight),
        enabled = isEnabled,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = ButtonContentPadding,
        elevation = flatElevation()
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    title: String,
    isEnabled: Boolean = true,
    leadingContent: (@Composable RowScope.() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = onClick ?: {},
        modifier = modifier.heightIn(min = ButtonHeight),
        enabled = isEnabled,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        contentPadding = ButtonContentPadding,
        elevation = flatElevation()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingContent != null) {
                leadingContent()
                Spacer(modifier = Modifier.width(BeFairDimension.Spacing.xs))
            }
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun DestructiveButton(
    modifier: Modifier = Modifier,
    title: String,
    isEnabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = onClick ?: {},
        modifier = modifier.heightIn(min = ButtonHeight),
        enabled = isEnabled,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.error
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
        contentPadding = ButtonContentPadding,
        elevation = flatElevation()
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    BeFairTheme {
        PrimaryButton(title = "Sign in")
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun SecondaryButtonPreview() {
    BeFairTheme {
        SecondaryButton(
            title = "Continue with Google",
            leadingContent = {
                Text(text = "G", style = MaterialTheme.typography.titleLarge)
            }
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun DestructiveButtonPreview() {
    BeFairTheme {
        DestructiveButton(title = "Delete Item")
    }
}
