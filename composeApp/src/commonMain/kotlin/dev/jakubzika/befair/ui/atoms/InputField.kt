package dev.jakubzika.befair.ui.atoms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "me@example.com"
) {
    Column(modifier = modifier) {
        Text(
            text = "EMAIL ADDRESS",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.2.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            ),
            modifier = Modifier.padding(start = 28.dp, bottom = 8.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color.White, CircleShape)
                .padding(horizontal = 28.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 18.sp
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EmailIcon(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    fontSize = 18.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
    }
}

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "••••••••"
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = "PASSWORD",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.2.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            ),
            modifier = Modifier.padding(start = 28.dp, bottom = 8.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color.White, CircleShape)
                .padding(horizontal = 28.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 18.sp
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LockIcon(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    fontSize = 18.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                    EyeIcon(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )
    }
}

@Composable
private fun EmailIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val rectW = w * 0.75f
        val rectH = h * 0.55f
        val left = (w - rectW) / 2
        val top = (h - rectH) / 2

        drawRoundRect(
            color = color,
            topLeft = Offset(left, top),
            size = Size(rectW, rectH),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Fill
        )

        val path = Path().apply {
            moveTo(left, top)
            lineTo(left + rectW / 2, top + rectH * 0.5f)
            lineTo(left + rectW, top)
        }
        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = 1.5.dp.toPx())
        )
    }
}

@Composable
private fun LockIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val bodyW = w * 0.6f
        val bodyH = h * 0.45f
        val bodyLeft = (w - bodyW) / 2
        val bodyTop = h * 0.45f

        drawRoundRect(
            color = color,
            topLeft = Offset(bodyLeft, bodyTop),
            size = Size(bodyW, bodyH),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Fill
        )

        drawPath(
            path = Path().apply {
                val arcRadius = bodyW * 0.35f
                val arcCenterX = w / 2
                val arcCenterY = bodyTop
                moveTo(arcCenterX - arcRadius, arcCenterY)
                cubicTo(
                    arcCenterX - arcRadius, arcCenterY - arcRadius * 1.5f,
                    arcCenterX + arcRadius, arcCenterY - arcRadius * 1.5f,
                    arcCenterX + arcRadius, arcCenterY
                )
            },
            color = color,
            style = Stroke(width = 2.dp.toPx())
        )

        drawCircle(
            color = Color.White,
            radius = 1.5.dp.toPx(),
            center = Offset(w / 2, bodyTop + bodyH * 0.4f)
        )
    }
}

@Composable
private fun EyeIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val path = Path().apply {
            moveTo(w * 0.1f, h / 2)
            quadraticBezierTo(w / 2, h * 0.15f, w * 0.9f, h / 2)
            quadraticBezierTo(w / 2, h * 0.85f, w * 0.1f, h / 2)
            close()
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 2.dp.toPx())
        )

        drawCircle(
            color = color,
            radius = w * 0.18f,
            center = Offset(w / 2, h / 2)
        )

        drawCircle(
            color = Color.White,
            radius = w * 0.08f,
            center = Offset(w / 2, h / 2)
        )
    }
}

@Preview
@Composable
private fun EmailInputFieldPreview() {
    BeFairTheme {
        var text by rememberSaveable { mutableStateOf("") }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp)
        ) {
            EmailInputField(
                value = text,
                onValueChange = { text = it }
            )
        }
    }
}

@Preview
@Composable
private fun PasswordInputFieldPreview() {
    BeFairTheme {
        var text by rememberSaveable { mutableStateOf("password") }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp)
        ) {
            PasswordInputField(
                value = text,
                onValueChange = { text = it }
            )
        }
    }
}
