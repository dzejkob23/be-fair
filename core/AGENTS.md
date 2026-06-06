# AGENTS.md — core module

Scoped guidance for the `core` module.
Read the root [`AGENTS.md`](../AGENTS.md) first for project-wide rules.

## Purpose

`core` is the **shared foundation** for all platforms (Android, iOS, JVM/server).
Place logic here only when it is genuinely needed by more than one target.
Mobile-only domain/data belongs in `app/shared`; server-only code belongs in `server`.

## Package Layout

```
core/src/
  commonMain/kotlin/dev/jakubzika/befair/
    di/AppContainer.kt          ← base dependency container
    data/network/
      HttpClientFactory.kt      ← expect declaration
    Constants.kt                ← shared constants (SERVER_PORT, etc.)
    Platform.kt                 ← expect Platform name
  androidMain/…                 ← actual implementations for Android
  iosMain/…                     ← actual implementations for iOS
  jvmMain/…                     ← actual implementations for JVM/server
  commonTest/…                  ← shared tests
```

## Dependency Container

`AppContainer` is the root DI container — **not a framework, just a plain class**.

Rules:
- All properties use `by lazy` to defer construction until first use.
- Add new cross-platform dependencies as `val` properties here.
- Mobile-specific dependencies go in `MobileAppContainer` (`app/shared`), not here.
- Never import Android or iOS symbols in `AppContainer`.

Example — adding a new shared service:
```kotlin
class AppContainer {
    val httpClient: HttpClient by lazy { createHttpClient() }
    val myService: MyService by lazy { MyServiceImpl(httpClient) }
}
```

## expect / actual Pattern

Use this for anything with a platform-specific implementation.

1. Declare `expect fun` or `expect class` in `commonMain`.
2. Provide `actual fun` or `actual class` in each of `androidMain`, `iosMain`, `jvmMain`.
3. Follow the `HttpClientFactory` pattern: keep the `expect` signature minimal.

```kotlin
// commonMain
expect fun createHttpClient(): HttpClient

// androidMain
actual fun createHttpClient(): HttpClient = HttpClient(OkHttp) { … }

// iosMain
actual fun createHttpClient(): HttpClient = HttpClient(Darwin) { … }

// jvmMain
actual fun createHttpClient(): HttpClient = HttpClient(CIO) { … }
```

## Constants

Add project-wide constants to `Constants.kt`. Reference them by name everywhere — never inline magic numbers or strings.

## Build & Test

```shell
# Unit tests (runs on JVM)
./gradlew :core:testDebugUnitTest

# Check compilation for all targets
./gradlew :core:compileKotlinAndroid :core:compileKotlinIosArm64 :core:compileKotlinJvm
```
