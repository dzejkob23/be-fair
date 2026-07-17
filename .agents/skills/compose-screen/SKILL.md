---
name: compose-screen
description: >
  Create new Compose Multiplatform screens for the BeFair app following atomic design,
  Clean Architecture, manual DI, and the M3 teal design system. Use when asked to add
  a new screen, page, feature view, or UI destination — even if the user says "add a
  page for X" or "create the Y feature" without mentioning Compose directly.
compatibility: Requires Kotlin, Compose Multiplatform, Material 3, and the BeFair project structure.
metadata:
  author: be-fair
  version: "2.0"
---

## When to use

Activate when:
- Adding a new screen or destination to the app.
- Creating a new feature that includes UI.
- Building a detail/list/form view for a domain entity.

For the data layer behind the screen (repository / use-case), see the `mobile-data-layer` skill.

## Step-by-step

### 1. Plan the layers

Decide what each layer needs before writing code:

| Layer | Location | What to create |
|-------|----------|----------------|
| Model | `app/shared/.../model/` | Data class for the domain entity (if new) |
| Domain | `app/shared/.../domain/repository/` | Repository interface (if new data source) |
| Data | `app/shared/.../data/repository/` | Repository implementation |
| DI | `app/shared/.../di/AppContainer.kt` | Wire the repository as a `lazy` property |
| Template | `app/shared/.../ui/templates/` | Layout only, all data as parameters |
| Screen | `app/shared/.../ui/screens/` | Reads state from DI, fills the template |

The `ui/molecules/`, `ui/organisms/`, and `ui/templates/` directories do not exist yet — create them the first time you need them.

### 2–5. Model, repository, DI

If the screen needs a new data source, follow the `mobile-data-layer` skill for the model,
domain interface, implementation, and DI wiring. Never instantiate repositories directly in composables.

### 6. Create the template (layout only)

Place in `ui/templates/<Name>Template.kt`.

- All data comes in as parameters. **No repository access, no coroutines, no state collection.**
- This keeps the layout previewable and testable in isolation.

### 7. Create the screen (data + template)

Place in `ui/screens/<Name>Screen.kt`. Access the DI container via CompositionLocal, collect
state, and pass plain data down to the template:

```kotlin
@Composable
fun ItemListScreen() {
    val container = LocalAppContainer.current
    val items by container.itemRepository.items.collectAsState()
    ItemListTemplate(items = items)
}
```

Place any reusable pieces at the lowest suitable atomic-design layer:

- `ui/atoms/` — Basic reusable UI pieces (Button, Colors, Theme). Unique components.
- `ui/molecules/` — Simple groups of atoms functioning together as a unit.
- `ui/organisms/` — Complex components composed of molecules, atoms, and/or other organisms.
- `ui/templates/` — Page-level layouts that place components and define content structure.
- `ui/screens/` — Specific instances of templates filled with real data.

Follow the design system (see `ui/DESIGN.md`):
- Colors: `MaterialTheme.colorScheme.*` (never hard-code hex)
- Typography: `MaterialTheme.typography.*`
- Spacing: `BeFairDimension.Spacing.*`
- Corner radius: `BeFairDimension.Radius.*`
- Cards: `elevation = 0.dp`, `containerColor = surfaceContainerLow`
- TopAppBar: `containerColor = surface` (not `primary`)
- User-facing text: define in `composeResources/values/strings.xml`, read via `stringResource(Res.string.<name>)` — never hardcode strings.

### 8. Register navigation

Add the new route to the `NavHost` in `App.kt` (Navigation 3).

### 9. Verify

- [ ] Model in `model/`, domain interface in `domain/repository/`, impl in `data/repository/`
- [ ] DI wired in `AppContainer` with `by lazy`
- [ ] Template is layout-only; screen owns the data
- [ ] No hard-coded colors/strings or direct repository instantiation in composables
- [ ] Build passes: `./gradlew :app:androidApp:assembleDebug` (or `:app:shared:testDebugUnitTest`)

## Gotchas

- `BeFairTypography()` is `@Composable` — don't assign it to a top-level val.
- `LocalAppContainer` uses `staticCompositionLocalOf` and will throw if no provider is set.
- TopAppBar container color is `surface`, not `primary` — teal is reserved for interactive accents.
- Inter font is loaded via Compose resources, not system fonts.
