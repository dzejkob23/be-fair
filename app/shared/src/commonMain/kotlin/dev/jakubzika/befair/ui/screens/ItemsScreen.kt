package dev.jakubzika.befair.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.jakubzika.befair.ui.templates.ItemsTab
import dev.jakubzika.befair.ui.templates.ItemsTemplate

// No item source is wired up yet, so the Clothes/Tools switcher always shows
// the "no data" hint per DESIGN.md — see OverviewScreen for the same pattern.
@Composable
fun ItemsScreen(
    onNavToItemDetailScreen: (id: String) -> Unit,
    onNavToAddNewItemScreen: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(ItemsTab.CLOTHES) }

    ItemsTemplate(
        selectedTab = selectedTab,
        onTabSelected = { selectedTab = it },
        onAddItem = onNavToAddNewItemScreen
    )
}
