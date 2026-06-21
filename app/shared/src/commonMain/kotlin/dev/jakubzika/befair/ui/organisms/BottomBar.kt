package dev.jakubzika.befair.ui.organisms

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.jakubzika.befair.ui.atoms.BeFairTheme
import dev.jakubzika.befair.ui.navigation.Items
import dev.jakubzika.befair.ui.navigation.Overview
import dev.jakubzika.befair.ui.navigation.Profile
import dev.jakubzika.befair.ui.navigation.Route
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.bottom_bar_items
import be_fair.app.shared.generated.resources.bottom_bar_overview
import be_fair.app.shared.generated.resources.bottom_bar_profile
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomBar(
    currentRoute: Route,
    onNavigateToOverview: () -> Unit,
    onNavigateToItems: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val overviewLabel = stringResource(Res.string.bottom_bar_overview)
    val itemsLabel = stringResource(Res.string.bottom_bar_items)
    val profileLabel = stringResource(Res.string.bottom_bar_profile)

    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentRoute == Overview,
            onClick = onNavigateToOverview,
            icon = { Icon(Icons.Default.Home, contentDescription = overviewLabel) },
            label = { Text(overviewLabel) }
        )
        NavigationBarItem(
            selected = currentRoute == Items,
            onClick = onNavigateToItems,
            icon = { Icon(Icons.Default.List, contentDescription = itemsLabel) },
            label = { Text(itemsLabel) }
        )
        NavigationBarItem(
            selected = currentRoute == Profile,
            onClick = onNavigateToProfile,
            icon = { Icon(Icons.Default.Person, contentDescription = profileLabel) },
            label = { Text(profileLabel) }
        )
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    BeFairTheme {
        BottomBar(
            currentRoute = Overview,
            onNavigateToOverview = {},
            onNavigateToItems = {},
            onNavigateToProfile = {}
        )
    }
}
