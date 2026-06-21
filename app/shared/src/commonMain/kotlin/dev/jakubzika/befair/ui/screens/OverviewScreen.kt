package dev.jakubzika.befair.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.jakubzika.befair.ui.atoms.PrimaryButton
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.screen_overview_go_to_item_detail
import be_fair.app.shared.generated.resources.screen_overview_go_to_items
import be_fair.app.shared.generated.resources.screen_overview_invested_sum
import be_fair.app.shared.generated.resources.screen_overview_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun OverviewScreen(
    onNavToItemDetailScreen: (id: String) -> Unit,
    onNavToItemsScreen: () -> Unit
) {
    // Placeholders for fields from class diagram
    val items = remember { listOf<Any>() }
    val investedSum = 0
    val avgCostPerWear = 0.0
    val toolsPricePerMonth = 0.0

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(Res.string.screen_overview_title))
            Text(text = stringResource(Res.string.screen_overview_invested_sum, investedSum.toString()))
            PrimaryButton(
                title = stringResource(Res.string.screen_overview_go_to_item_detail),
                onClick = { onNavToItemDetailScreen("1") }
            )
            PrimaryButton(title = stringResource(Res.string.screen_overview_go_to_items), onClick = onNavToItemsScreen)
        }
    }
}
