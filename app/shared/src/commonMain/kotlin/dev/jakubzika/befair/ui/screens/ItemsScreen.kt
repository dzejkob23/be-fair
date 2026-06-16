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
fun ItemsScreen(
    onNavToItemDetailScreen: (id: String) -> Unit,
    onNavToAddNewItemScreen: () -> Unit
) {
    val items = remember { listOf<Any>() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Items Screen")
            PrimaryButton(title = "Go to Item Detail (ID: 2)", onClick = { onNavToItemDetailScreen("2") })
            PrimaryButton(title = "Add New Item", onClick = onNavToAddNewItemScreen)
        }
    }
}
