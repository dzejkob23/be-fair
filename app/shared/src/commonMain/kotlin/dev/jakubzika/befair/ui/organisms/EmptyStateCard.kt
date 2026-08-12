package dev.jakubzika.befair.ui.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.empty_state_mark
import be_fair.app.shared.generated.resources.screen_overview_empty_add_first_item
import be_fair.app.shared.generated.resources.screen_overview_empty_description
import be_fair.app.shared.generated.resources.screen_overview_empty_welcome
import dev.jakubzika.befair.ui.atoms.BeFairDimension
import dev.jakubzika.befair.ui.atoms.BeFairTheme
import dev.jakubzika.befair.ui.atoms.PrimaryButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// "Empty state (no data)" per DESIGN.md Components: white surface card, 4px
// radius (rounded.md), 32px padding (spacing.xl), centred mark + headline-lg
// welcome + body-md explanation + a single full-width primary action.
@Composable
fun EmptyStateCard(
    welcomeText: String,
    description: String,
    onAddFirstItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BeFairDimension.Radius.small))
            .background(MaterialTheme.colorScheme.surface)
            .padding(BeFairDimension.Spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Res.drawable.empty_state_mark),
            contentDescription = null,
            modifier = Modifier.size(44.dp),
            tint = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.md))

        Text(
            text = welcomeText,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.sm))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(BeFairDimension.Spacing.lg))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.screen_overview_empty_add_first_item),
            onClick = onAddFirstItem
        )
    }
}

@Preview(backgroundColor = 0xF5F5F2, showBackground = true)
@Composable
private fun EmptyStateCardPreview() {
    BeFairTheme {
        EmptyStateCard(
            welcomeText = stringResource(Res.string.screen_overview_empty_welcome, "Anna"),
            description = stringResource(Res.string.screen_overview_empty_description, "BeFair"),
            onAddFirstItem = {}
        )
    }
}
