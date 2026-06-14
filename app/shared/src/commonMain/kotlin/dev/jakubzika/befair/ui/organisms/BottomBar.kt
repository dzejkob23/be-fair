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
import dev.jakubzika.befair.ui.navigation.Items
import dev.jakubzika.befair.ui.navigation.Overview
import dev.jakubzika.befair.ui.navigation.Profile
import dev.jakubzika.befair.ui.navigation.Route

@Composable
fun BottomBar(
    currentRoute: Route,
    onNavigateToOverview: () -> Unit,
    onNavigateToItems: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentRoute == Overview,
            onClick = onNavigateToOverview,
            icon = { Icon(Icons.Default.Home, contentDescription = "Overview") },
            label = { Text("Overview") }
        )
        NavigationBarItem(
            selected = currentRoute == Items,
            onClick = onNavigateToItems,
            icon = { Icon(Icons.Default.List, contentDescription = "Items") },
            label = { Text("Items") }
        )
        NavigationBarItem(
            selected = currentRoute == Profile,
            onClick = onNavigateToProfile,
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}
