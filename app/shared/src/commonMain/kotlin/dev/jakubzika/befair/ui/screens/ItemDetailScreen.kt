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
import be_fair.app.shared.generated.resources.screen_item_detail_back
import be_fair.app.shared.generated.resources.screen_item_detail_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ItemDetailScreen(
    id: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(Res.string.screen_item_detail_title, id))
            PrimaryButton(title = stringResource(Res.string.screen_item_detail_back), onClick = onBack)
        }
    }
}
