package dev.jakubzika.befair.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.brand_wordmark
import dev.jakubzika.befair.ui.LocalAppContainer
import dev.jakubzika.befair.ui.templates.OverviewEmptyTemplate
import org.jetbrains.compose.resources.stringResource

@Composable
fun OverviewScreen(
    onNavToAddNewItemScreen: () -> Unit
) {
    val container = LocalAppContainer.current
    val userName = remember { container.userProfileStorage.getDisplayName().orEmpty() }

    // No item source is wired up yet, so a freshly registered user always
    // lands here — the "no data" screen per DESIGN.md's empty-state component.
    OverviewEmptyTemplate(
        userName = userName,
        appName = stringResource(Res.string.brand_wordmark),
        onAddFirstItem = onNavToAddNewItemScreen
    )
}
