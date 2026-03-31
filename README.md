# Be-Fair

Be-Fair is a Kotlin Multiplatform project with three runtime targets:
- Android app
- iOS app
- Ktor backend server

The shared UI is built with Compose Multiplatform (Material 3), and shared business logic lives in Kotlin Multiplatform modules.

## Project Structure

- [`androidApp`](./androidApp) - Android entry app (`MainActivity`, Android manifest/resources).
- [`composeApp`](./composeApp/src) - shared Compose UI for Android and iOS.
- [`shared`](./shared/src) - shared Kotlin logic used by mobile and server modules.
- [`server`](./server/src/main/kotlin) - Ktor server (Netty).
- [`iosApp`](./iosApp) - Xcode project and iOS entry point.

## Source Set Guide (KMP)

Most modules follow Kotlin Multiplatform source sets:
- `commonMain` - code shared by all targets.
- `androidMain` - Android-only code.
- `iosMain` - iOS-only code.
- `jvmMain` - JVM-only code (mainly server/JVM scenarios).
- `commonTest` - shared tests.

## Build and Run

### Android

```shell
./gradlew :androidApp:assembleDebug
```

### Server

```shell
./gradlew :server:run
```

### iOS

Open [`iosApp`](./iosApp) in Xcode and run from Xcode.

## Testing

Run all tests:

```shell
./gradlew test
```

Run tests by module:

```shell
./gradlew :composeApp:testDebugUnitTest
./gradlew :server:test
./gradlew :shared:testDebugUnitTest
```

## Tech Snapshot

- Kotlin `2.3.0`
- Compose Multiplatform `1.9.3`
- Ktor `3.3.3`
- Android Gradle Plugin `9.0.1`
- Android compileSdk `36`, minSdk `24`

## For AI Agent Context

If you are working with an AI coding agent, see [`AGENTS.md`](./AGENTS.md) for repository-specific implementation guidance.
