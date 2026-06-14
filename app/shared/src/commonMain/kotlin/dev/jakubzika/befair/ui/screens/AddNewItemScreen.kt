package dev.jakubzika.befair.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.jakubzika.befair.ui.atoms.PrimaryButton

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
            Text(text = "Add New Item Screen")
            PrimaryButton(title = "Create Item", onClick = onCreateItem)
            PrimaryButton(title = "Back", onClick = onBack)
        }
    }
}
