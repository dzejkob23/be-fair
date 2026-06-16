---
name: BeFair
description: Precision Utility — clean, data-centric, teal-based M3 design system for a cost-per-use tracker.
colors:
  primary: "#005050"
  on-primary: "#FFFFFF"
  primary-container: "#006A6A"
  on-primary-container: "#97E7E6"
  secondary: "#4A6363"
  on-secondary: "#FFFFFF"
  secondary-container: "#CCE8E7"
  on-secondary-container: "#506969"
  tertiary: "#334863"
  on-tertiary: "#FFFFFF"
  tertiary-container: "#4B607C"
  on-tertiary-container: "#C5DBFB"
  error: "#BA1A1A"
  on-error: "#FFFFFF"
  error-container: "#FFDAD6"
  on-error-container: "#93000A"
  background: "#F8FAFA"
  on-background: "#191C1D"
  surface: "#F8FAFA"
  on-surface: "#191C1D"
  surface-variant: "#E1E3E3"
  on-surface-variant: "#3E4948"
  outline: "#6E7979"
  outline-variant: "#BEC9C8"
  inverse-surface: "#2E3131"
  inverse-on-surface: "#EFF1F1"
  inverse-primary: "#84D4D3"
  surface-container-lowest: "#FFFFFF"
  surface-container-low: "#F2F4F4"
  surface-container: "#ECEEEE"
  surface-container-high: "#E6E8E9"
  surface-container-highest: "#E1E3E3"
  surface-dim: "#D8DADA"
  surface-bright: "#F8FAFA"
  scrim: "#000000"
typography:
  display-large:
    fontFamily: Inter
    fontSize: 57sp
    lineHeight: 64sp
    fontWeight: 400
    letterSpacing: -0.25sp
  headline-large:
    fontFamily: Inter
    fontSize: 32sp
    lineHeight: 40sp
    fontWeight: 400
    letterSpacing: 0sp
  headline-medium:
    fontFamily: Inter
    fontSize: 28sp
    lineHeight: 36sp
    fontWeight: 400
    letterSpacing: 0sp
  title-large:
    fontFamily: Inter
    fontSize: 22sp
    lineHeight: 28sp
    fontWeight: 500
    letterSpacing: 0sp
  body-large:
    fontFamily: Inter
    fontSize: 16sp
    lineHeight: 24sp
    fontWeight: 400
    letterSpacing: 0.5sp
  body-medium:
    fontFamily: Inter
    fontSize: 14sp
    lineHeight: 20sp
    fontWeight: 400
    letterSpacing: 0.25sp
  label-large:
    fontFamily: Inter
    fontSize: 14sp
    lineHeight: 20sp
    fontWeight: 500
    letterSpacing: 0.1sp
  label-small:
    fontFamily: Inter
    fontSize: 11sp
    lineHeight: 16sp
    fontWeight: 500
    letterSpacing: 0.5sp
rounded:
  sm: 4dp
  md: 8dp
  lg: 12dp
  full: 9999dp
spacing:
  unit: 4dp
  xs: 4dp
  sm: 8dp
  md: 16dp
  lg: 24dp
  xl: 32dp
components:
  top-app-bar:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.on-surface}"
    typography: "{typography.title-large}"
  bottom-nav-bar:
    backgroundColor: "{colors.surface-container}"
  bottom-nav-item-selected:
    textColor: "{colors.on-secondary-container}"
    backgroundColor: "{colors.secondary-container}"
    typography: "{typography.label-small}"
  bottom-nav-item-unselected:
    textColor: "{colors.on-surface-variant}"
    typography: "{typography.label-small}"
  card:
    backgroundColor: "{colors.surface-container-low}"
    rounded: "{rounded.md}"
    padding: 16dp
  fab:
    backgroundColor: "{colors.primary-container}"
    textColor: "{colors.on-primary-container}"
    rounded: "{rounded.lg}"
---

## Overview

Precision Utility meets Material Design 3. The UI is clean, data-centric, and trustworthy —
teal accents reserved for interactive elements against neutral surfaces.

## Colors

The palette is a custom teal-based M3 scheme derived from Stitch design tokens.

- **Primary (#005050):** Deep teal for interactive accents and emphasis.
- **Secondary (#4A6363):** Muted teal-grey for secondary controls and supporting UI.
- **Tertiary (#334863):** Slate blue for complementary accents (charts, badges).
- **Surface (#F8FAFA):** Near-white background — not pure white, slightly warm.
- **Error (#BA1A1A):** Standard M3 error red.

Dark mode colors are defined in `Color.kt` alongside the light palette.

The `primary-fixed`, `secondary-fixed`, and `tertiary-fixed` families are declared as
standalone `val` declarations for future component use. These colors do not adapt between
light and dark mode per M3 specification.

### Implementation

All colors live in `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/atoms/Color.kt`.
Always access colors via `MaterialTheme.colorScheme.*` — never hard-code hex values in composables.

## Typography

Font family: **Inter**, loaded from `app/shared/src/commonMain/composeResources/font/` via
Compose resources. All other M3 type roles retain Material 3 defaults.

Implementation: `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/atoms/Type.kt`.
Access via `MaterialTheme.typography.*`.

## Layout & Spacing

Spacing tokens live in `BeFairDimension.Spacing` and corner radius tokens in
`BeFairDimension.Radius`, both defined in
`app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/atoms/Dimension.kt`.

- `md` (16.dp) is the standard mobile gutter/margin.
- `lg` (24.dp) is the desktop margin.

## Elevation & Depth

Use `elevation = 0.dp` on cards and rely on `surfaceContainerLow` vs `background` color
contrast for visual separation. This follows M3's tonal elevation model.

## Components

### TopAppBar

Container color is `surface`, not `primary` — keeps teal reserved for interactive accents.

```kotlin
TopAppBar(
    title = { Text("Screen Title", style = MaterialTheme.typography.titleLarge) },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
    )
)
```

### BottomNavigationBar

Four destinations: Items, Add, Statistics, Profile.

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

### Cards

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

### Floating Action Button

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

## Do's and Don'ts

- **Do** access colors via `MaterialTheme.colorScheme.*` and typography via `MaterialTheme.typography.*`.
- **Do** use `BeFairDimension.Spacing.*` and `BeFairDimension.Radius.*` for layout values.
- **Do** use `elevation = 0.dp` and tonal surface colors for card separation.
- **Don't** hard-code hex color values in composables.
- **Don't** use `primary` as TopAppBar container color — use `surface`.
- **Don't** introduce new fonts; Inter is the sole typeface.
