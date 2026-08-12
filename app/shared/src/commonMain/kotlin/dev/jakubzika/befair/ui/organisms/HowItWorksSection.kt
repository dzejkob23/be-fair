package dev.jakubzika.befair.ui.organisms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jakubzika.befair.ui.atoms.BeFairDimension
import dev.jakubzika.befair.ui.atoms.BeFairTheme

data class HowItWorksStep(val title: String, val description: String)

private val StepRowPadding = 14.dp
private val StepNumberSize = 22.dp

// "How it works" list per DESIGN.md Components: numbered `step-item` rows with
// a 22px circular hairline-outlined `step-number`, grouped in a hairline-divided
// surface card under a label-caps section header.
@Composable
fun HowItWorksSection(
    headerTitle: String,
    steps: List<HowItWorksStep>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = headerTitle.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.sm))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(BeFairDimension.Radius.small))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            steps.forEachIndexed { index, step ->
                StepRow(number = index + 1, step = step)
                if (index != steps.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }
}

@Composable
private fun StepRow(number: Int, step: HowItWorksStep) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(StepRowPadding),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(StepNumberSize)
                .clip(CircleShape)
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(BeFairDimension.Spacing.sm))

        Column {
            Text(
                text = step.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = step.description,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(backgroundColor = 0xF5F5F2, showBackground = true)
@Composable
private fun HowItWorksSectionPreview() {
    BeFairTheme {
        HowItWorksSection(
            headerTitle = "How it works",
            steps = listOf(
                HowItWorksStep("Add what you own", "Name, price paid, purchase date."),
                HowItWorksStep("Log each wear or use", "One tap on +1 in your items list."),
                HowItWorksStep("See the true cost", "Cost per wear for clothes, per month for tools.")
            )
        )
    }
}
