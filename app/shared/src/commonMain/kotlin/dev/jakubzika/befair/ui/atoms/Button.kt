package dev.jakubzika.befair.ui.atoms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    title: String,
    isEnabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = onClick ?: {},
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        content = {
            Text(text = title)
        },
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 16.dp
        )
    )
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    title: String,
    isEnabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = onClick ?: {},
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 16.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
            Spacer(modifier = Modifier.width(8.dp))
            ArrowRightIcon(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun ArrowRightIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = 2.dp.toPx()

        drawPath(
            path = Path().apply {
                moveTo(0f, h / 2)
                lineTo(w, h / 2)
                moveTo(w * 0.6f, h * 0.2f)
                lineTo(w, h / 2)
                lineTo(w * 0.6f, h * 0.8f)
            },
            color = color,
            style = Stroke(width = strokeWidth)
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    BeFairTheme {
        PrimaryButton(title = "Primary Button")
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun ActionButtonPreview() {
    BeFairTheme {
        ActionButton(title = "Log In")
    }
}
