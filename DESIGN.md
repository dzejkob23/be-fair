# Product Requirements Document: BeFair

## 1. Executive Summary
**BeFair** is a cost-per-use and ethical consumption tracking app designed to help users understand the true value of their purchases. By measuring usage frequency (e.g., wearing clothes, using tools) against the initial cost, BeFair empowers users to make more sustainable, high-value buying decisions while promoting fairness to the world.

## 2. Project Vision & Goals
*   **Vision:** To shift consumer behavior from "fast consumption" to "long-term value" through data-driven insights.
*   **Primary Goal:** Provide a simple, Material Design-based interface for logging items and tracking their cost-per-use over time.
*   **Secondary Goal:** Integrate "fairness" metrics that consider the environmental and ethical impact of products.

## 3. Target Audience
*   **Sustainable Shoppers:** Individuals looking to reduce waste by buying higher-quality items that last longer.
*   **Budget-Conscious Users:** People wanting to see the actual ROI of their investments (e.g., "Is this $200 jacket actually cheaper than a $50 one if I wear it 10x more?").
*   **Minimalists:** Users who want to curate a smaller, more meaningful collection of belongings.

## 4. Key Features (Phase 1)
*   **Item Management:**
    *   Add new items with price, category, and initial details.
    *   Remove/Archive items when they are no longer in use.
    *   List view of all tracked items.
*   **Usage Tracking:**
    *   One-tap logging for "usage" (e.g., wearing a garment, using a device).
    *   Logging of "maintenance" events (e.g., washing, repairs) to see total cost of ownership.
*   **Value Analytics:**
    *   Real-time calculation of "Cost-per-Use."
    *   Visual trends showing usage frequency vs. price.
*   **User Authentication:**
    *   Secure login/signup to sync data across devices.

## 5. Design & Brand Identity
*   **Brand Name:** BeFair (formerly ValueTrack).
*   **Visual Style:** Precision Utility — Clean, data-centric, professional, and trustworthy.
*   **Color Palette:** Primary Teal (#006a6a) on light surfaces.
*   **Design System:** Material Design 3 (M3).
*   **Logo Direction:** Minimalist 'B' or 'BF' monograms incorporating scales of justice, data bars, or checkmarks.

## 6. Technical Requirements
*   **Platform:** Mobile-first (iOS and Android).
*   **Navigation:** Bottom Navigation Bar for primary destinations (Items, Add, Trends, Settings).
*   **Components:** Top App Bar for branding and profile access.

## 7. Success Metrics
*   **Engagement:** Number of items added per user.
*   **Retention:** Frequency of usage logs.
*   **Impact:** Average decrease in cost-per-use across a user's inventory over 6 months.

## 8. Design System Implementation

The BeFair design system is a Material Design 3 implementation with a custom teal-based color palette
derived from Stitch design tokens. All tokens live under
`composeApp/src/commonMain/kotlin/dev/jakubzika/befair/ui/atoms/`.

### Color Palette (Teal — M3 Custom)

| Role | Light | Dark |
|---|---|---|
| primary | `#005050` | `#84D4D3` |
| onPrimary | `#FFFFFF` | `#003737` |
| primaryContainer | `#006A6A` | `#004F4F` |
| onPrimaryContainer | `#97E7E6` | `#9DECEB` |
| secondary | `#4A6363` | `#B1CCCB` |
| onSecondary | `#FFFFFF` | `#1C3535` |
| secondaryContainer | `#CCE8E7` | `#324B4B` |
| onSecondaryContainer | `#506969` | `#CDE9E8` |
| tertiary | `#334863` | `#B2C8E8` |
| onTertiary | `#FFFFFF` | `#1C3149` |
| tertiaryContainer | `#4B607C` | `#334863` |
| onTertiaryContainer | `#C5DBFB` | `#D2E4FF` |
| error | `#BA1A1A` | `#FFB4AB` |
| onError | `#FFFFFF` | `#690005` |
| errorContainer | `#FFDAD6` | `#93000A` |
| onErrorContainer | `#93000A` | `#FFDAD6` |
| background | `#F8FAFA` | `#101414` |
| onBackground | `#191C1D` | `#E1E3E3` |
| surface | `#F8FAFA` | `#101414` |
| onSurface | `#191C1D` | `#E1E3E3` |
| surfaceVariant | `#E1E3E3` | `#3E4948` |
| onSurfaceVariant | `#3E4948` | `#BEC9C8` |
| outline | `#6E7979` | `#889392` |
| outlineVariant | `#BEC9C8` | `#3E4948` |
| inverseSurface | `#2E3131` | `#E1E3E3` |
| inverseOnSurface | `#EFF1F1` | `#2E3131` |
| inversePrimary | `#84D4D3` | `#005050` |
| surfaceContainerLowest | `#FFFFFF` | `#0B0F0F` |
| surfaceContainerLow | `#F2F4F4` | `#191C1D` |
| surfaceContainer | `#ECEEEE` | `#1D2020` |
| surfaceContainerHigh | `#E6E8E9` | `#272B2B` |
| surfaceContainerHighest | `#E1E3E3` | `#323535` |
| surfaceDim | `#D8DADA` | `#101414` |
| surfaceBright | `#F8FAFA` | `#363A3A` |
| scrim | `#000000` | `#000000` |

The `primary-fixed`, `secondary-fixed`, and `tertiary-fixed` families are also declared in
`Color.kt` as standalone top-level `val` declarations for future component use. These colors
do not adapt between light and dark mode per M3 specification.

### Type Scale

Font family: **Inter**, loaded from `composeApp/src/commonMain/composeResources/font/` via compose resources.

| M3 Role | Size | Line Height | Weight | Tracking | Stitch Name |
|---|---|---|---|---|---|
| `displayLarge` | 57sp | 64sp | 400 | -0.25sp | display-lg |
| `headlineLarge` | 32sp | 40sp | 400 | 0sp | headline-lg |
| `headlineMedium` | 28sp | 36sp | 400 | 0sp | headline-lg-mobile |
| `titleLarge` | 22sp | 28sp | 500 | 0sp | title-lg |
| `bodyLarge` | 16sp | 24sp | 400 | 0.5sp | body-lg |
| `bodyMedium` | 14sp | 20sp | 400 | 0.25sp | body-md |
| `labelLarge` | 14sp | 20sp | 500 | 0.1sp | label-lg |
| `labelSmall` | 11sp | 16sp | 500 | 0.5sp | label-sm |

All other M3 type roles retain Material 3 defaults.

### Spacing Tokens (`BeFairDimension.Spacing`)

| Token | Value | Stitch Name |
|---|---|---|
| `unit` | 4.dp | unit |
| `xs` | 4.dp | xs |
| `sm` | 8.dp | sm |
| `md` | 16.dp | md / gutter / margin-mobile |
| `lg` | 24.dp | lg / margin-desktop |
| `xl` | 32.dp | xl |

### Corner Radius Tokens (`BeFairDimension.Radius`)

| Token | Value | Stitch Name |
|---|---|---|
| `small` | 4.dp | DEFAULT |
| `medium` | 8.dp | lg |
| `large` | 12.dp | xl |
| `full` | 9999.dp | full (pill shape) |

### Key UI Component Patterns

Access colors via `MaterialTheme.colorScheme.*` and typography via `MaterialTheme.typography.*`.
Never hard-code hex values in composables — always reference the scheme.

#### TopAppBar

```kotlin
TopAppBar(
    title = { Text("Screen Title", style = MaterialTheme.typography.titleLarge) },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
    )
)
```

Container color is `surface`, not `primary` — keeps teal reserved for interactive accents.

#### BottomNavigationBar

```kotlin
NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
    NavigationBarItem(
        selected = isSelected,
        onClick = { /* navigate */ },
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )
}
```

Four destinations: Items, Add, Statistics, Profile.

#### Cards

```kotlin
Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(BeFairDimension.Radius.medium),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
) {
    Column(modifier = Modifier.padding(BeFairDimension.Spacing.md)) { /* content */ }
}
```

Use `elevation = 0.dp` and rely on `surfaceContainerLow` vs `background` color contrast for elevation.

#### Floating Action Button

```kotlin
FloatingActionButton(
    onClick = { /* action */ },
    shape = RoundedCornerShape(BeFairDimension.Radius.large),
    containerColor = MaterialTheme.colorScheme.primaryContainer,
    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    Icon(Icons.Default.Add, contentDescription = "Add item")
}
```