# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Be-Fair is a Kotlin Multiplatform (KMP) project targeting Android, iOS, and a Ktor backend server. The UI layer uses Compose Multiplatform with Material3. Package name: `dev.jakubzika.befair`.

## Build & Run Commands

```shell
# Android
./gradlew :androidApp:assembleDebug

# Server (Ktor on Netty, port 8080)
./gradlew :server:run

# iOS — open iosApp/ in Xcode and run from there

# Run all tests
./gradlew test

# Run tests per module
./gradlew :composeApp:testDebugUnitTest
./gradlew :server:test
./gradlew :shared:testDebugUnitTest
```

## Architecture

Four Gradle modules (`settings.gradle.kts`):

- **androidApp** — Android application entry point (`MainActivity`, manifest, resources). Depends on `composeApp`.
- **composeApp** — Compose Multiplatform UI library (Android + iOS). Contains all screens and the design system. Uses `com.android.kotlin.multiplatform.library` plugin.
- **shared** — Pure Kotlin library shared across all targets (Android, iOS, JVM). Houses platform abstractions (`expect`/`actual` for `Platform`), constants, and shared logic. Uses `com.android.kotlin.multiplatform.library` plugin.
- **server** — Ktor server application (JVM/Netty). Depends on `shared`.

### Multiplatform Source Sets

Each module uses KMP source sets: `commonMain`, `androidMain`, `iosMain`, `jvmMain`, plus `commonTest` for shared tests. Platform-specific code uses Kotlin `expect`/`actual` declarations.

### UI — Atomic Design in composeApp

UI components live under `composeApp/src/commonMain/kotlin/dev/jakubzika/befair/ui/` and follow atomic design:

- **atoms/** — Primitive components: `Button.kt` (PrimaryButton, ActionButton), `InputField.kt` (EmailInputField, PasswordInputField), `Color.kt`, `Theme.kt`, `Type.kt`, `Dimension.kt`
- **molecules/** — Composite components (planned)
- **organisms/** — Complex composed sections (planned)
- **templates/** — Page layout templates (e.g., `LoginTemplate`)
- **screens/** — Full screens: `LoginScreen`, `HomeScreen`, `RegistrationScreen`, `ItemDetailScreen`, `NewItemScreen`, `StatisticsScreen`

### Key Tech Versions

Managed centrally in `gradle/libs.versions.toml`:
- Kotlin 2.3.0, Compose Multiplatform 1.9.3, Ktor 3.3.3, AGP 9.0.1, Gradle 9.3.1
- Android: compileSdk 36, minSdk 24, JVM target 11
