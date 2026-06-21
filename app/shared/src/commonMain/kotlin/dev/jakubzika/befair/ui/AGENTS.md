# AGENTS.md

Guidance for AI agents working on UI components, templates, and screens.

# Rules
When editing UI, preserve this structure and place new components at the lowest suitable layer.

## Strings
Never hardcode user-facing text directly in a composable. Define every string in
[`app/shared/src/commonMain/composeResources/values/strings.xml`](app/shared/src/commonMain/composeResources/values/strings.xml)
and read it via `stringResource(Res.string.<name>)`. This is the Compose Multiplatform
resource file (not `app/androidApp/src/main/res/values/strings.xml`, which is Android-only
and unreachable from `commonMain`) — it generates a `Res.string.*` API shared by Android and iOS.

## Design system
Read [`DESIGN.md`](app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/DESIGN.md).

## Component layers
- `atoms/` - Basic reusable UI pieces (`Button`, `Colors`, `Theme`, `Title`, etc.). Those components are unique.
- `molecules/` - Are relatively simple groups of `atoms` functioning together as a unit.
- `organisms/` - Are relatively complex UI components composed of groups of `molecules` and/or `atoms` and/or other `organisms`.
- `templates/` - Templates are page-level objects that place components into a layout and articulate the design’s underlying content structure. It's a combination of `atoms`, `molecules`, and `organisms` (for example `LoginTemplate`).
- `screens/` - Pages are specific instances of templates that show what a UI looks like with real representative content in place. Displays `template` and fills it by data (for example `LoginScreen`, `HomeScreen`, etc.).


