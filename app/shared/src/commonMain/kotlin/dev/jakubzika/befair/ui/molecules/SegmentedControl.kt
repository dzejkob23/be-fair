package dev.jakubzika.befair.ui.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jakubzika.befair.ui.atoms.BeFairDimension
import dev.jakubzika.befair.ui.atoms.BeFairTheme

private val TrackHeight = 36.dp
private val TrackInset = 2.dp
private val TrackShape = RoundedCornerShape(BeFairDimension.Radius.small)

// "Segmented control" per DESIGN.md Components: a track at 36dp with a 2dp
// inset; the active segment lifts onto a surface with Bold text and a faint shadow.
@Composable
fun SegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TrackHeight)
            .clip(TrackShape)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(TrackInset)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .then(
                        if (isSelected) {
                            Modifier
                                .shadow(1.dp, TrackShape)
                                .clip(TrackShape)
                                .background(MaterialTheme.colorScheme.surface)
                        } else {
                            Modifier
                        }
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple()
                    ) { onOptionSelected(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    style = if (isSelected) {
                        MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    } else {
                        MaterialTheme.typography.titleSmall
                    },
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

@Preview(backgroundColor = 0xF5F5F2, showBackground = true)
@Composable
private fun SegmentedControlPreview() {
    BeFairTheme {
        SegmentedControl(
            options = listOf("Clothes", "Tools"),
            selectedIndex = 0,
            onOptionSelected = {}
        )
    }
}
