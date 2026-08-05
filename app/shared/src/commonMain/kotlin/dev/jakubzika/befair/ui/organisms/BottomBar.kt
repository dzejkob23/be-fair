package dev.jakubzika.befair.ui.organisms

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jakubzika.befair.ui.atoms.BeFairTheme
import dev.jakubzika.befair.ui.atoms.LocalBeFairExtendedColors
import dev.jakubzika.befair.ui.navigation.Items
import dev.jakubzika.befair.ui.navigation.Overview
import dev.jakubzika.befair.ui.navigation.Profile
import dev.jakubzika.befair.ui.navigation.Route
import be_fair.app.shared.generated.resources.Res
import be_fair.app.shared.generated.resources.bottom_bar_items
import be_fair.app.shared.generated.resources.bottom_bar_overview
import be_fair.app.shared.generated.resources.bottom_bar_profile
import be_fair.app.shared.generated.resources.nav_items_active
import be_fair.app.shared.generated.resources.nav_items_inactive
import be_fair.app.shared.generated.resources.nav_overview_active
import be_fair.app.shared.generated.resources.nav_overview_inactive
import be_fair.app.shared.generated.resources.nav_profile_active
import be_fair.app.shared.generated.resources.nav_profile_inactive
import org.jetbrains.compose.resources.painterResource
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

    val hairlineColor = MaterialTheme.colorScheme.outlineVariant
    val density = LocalDensity.current
    val strokeWidthPx = with(density) { 1.dp.toPx() }

    val activeColor = MaterialTheme.colorScheme.onSurface
    val inactiveColor = LocalBeFairExtendedColors.current.ink3

    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = activeColor,
        selectedTextColor = activeColor,
        unselectedIconColor = inactiveColor,
        unselectedTextColor = inactiveColor,
        indicatorColor = Color.Transparent
    )

    NavigationBar(
        modifier = modifier.drawBehind {
            drawLine(
                color = hairlineColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = strokeWidthPx
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        val overviewSelected = currentRoute == Overview
        NavigationBarItem(
            selected = overviewSelected,
            onClick = onNavigateToOverview,
            icon = {
                Icon(
                    painter = painterResource(
                        if (overviewSelected) Res.drawable.nav_overview_active else Res.drawable.nav_overview_inactive
                    ),
                    contentDescription = overviewLabel
                )
            },
            label = {
                Text(
                    text = overviewLabel,
                    style = if (overviewSelected) {
                        MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    } else {
                        MaterialTheme.typography.labelSmall
                    }
                )
            },
            colors = itemColors
        )

        val itemsSelected = currentRoute == Items
        NavigationBarItem(
            selected = itemsSelected,
            onClick = onNavigateToItems,
            icon = {
                Icon(
                    painter = painterResource(
                        if (itemsSelected) Res.drawable.nav_items_active else Res.drawable.nav_items_inactive
                    ),
                    contentDescription = itemsLabel
                )
            },
            label = {
                Text(
                    text = itemsLabel,
                    style = if (itemsSelected) {
                        MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    } else {
                        MaterialTheme.typography.labelSmall
                    }
                )
            },
            colors = itemColors
        )

        val profileSelected = currentRoute == Profile
        NavigationBarItem(
            selected = profileSelected,
            onClick = onNavigateToProfile,
            icon = {
                Icon(
                    painter = painterResource(
                        if (profileSelected) Res.drawable.nav_profile_active else Res.drawable.nav_profile_inactive
                    ),
                    contentDescription = profileLabel
                )
            },
            label = {
                Text(
                    text = profileLabel,
                    style = if (profileSelected) {
                        MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    } else {
                        MaterialTheme.typography.labelSmall
                    }
                )
            },
            colors = itemColors
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
