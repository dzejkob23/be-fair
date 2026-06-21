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
import be_fair.app.shared.generated.resources.screen_items_add_new_item
import be_fair.app.shared.generated.resources.screen_items_go_to_item_detail
import be_fair.app.shared.generated.resources.screen_items_title
import org.jetbrains.compose.resources.stringResource

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
            Text(text = stringResource(Res.string.screen_items_title))
            PrimaryButton(
                title = stringResource(Res.string.screen_items_go_to_item_detail),
                onClick = { onNavToItemDetailScreen("2") }
            )
            PrimaryButton(title = stringResource(Res.string.screen_items_add_new_item), onClick = onNavToAddNewItemScreen)
        }
    }
}
