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
            Text(text = "Overview Screen")
            Text(text = "Invested Sum: $investedSum")
            PrimaryButton(title = "Go to Item Detail (ID: 1)", onClick = { onNavToItemDetailScreen("1") })
            PrimaryButton(title = "Go to Items", onClick = onNavToItemsScreen)
        }
    }
}
