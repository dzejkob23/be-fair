package dev.jakubzika.befair.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.jakubzika.befair.ui.atoms.PrimaryButton
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.screen_add_new_item_back
import be_fair.app.shared.generated.resources.screen_add_new_item_create
import be_fair.app.shared.generated.resources.screen_add_new_item_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddNewItemScreen(
    onCreateItem: () -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(Res.string.screen_add_new_item_title))
            PrimaryButton(title = stringResource(Res.string.screen_add_new_item_create), onClick = onCreateItem)
            PrimaryButton(title = stringResource(Res.string.screen_add_new_item_back), onClick = onBack)
        }
    }
}
