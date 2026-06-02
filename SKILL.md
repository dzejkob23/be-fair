---
name: be-fair
description: >
  Agent workflow recipes for the BeFair Kotlin Multiplatform project (Android, iOS, Ktor server).
  Use this skill when adding or modifying features — including new screens, domain models,
  repositories, use-cases, API endpoints, or shared core logic. Also use when unsure which
  module, source set, or layer a change belongs in, or when wiring DI in AppContainer or
  MobileAppContainer. Applies to all tasks touching app/shared, core, or server modules.
compatibility: BeFair KMP project. Requires Gradle, Android Studio or Xcode for mobile targets.
metadata:
  project: be-fair
  package: dev.jakubzika.befair
---

# BeFair Agent Workflows

Step-by-step recipes for common tasks in this codebase.
Read [AGENTS.md](./AGENTS.md) for architecture rules and the Safe Change Checklist.

---

## Module Placement — decide this first

| What you're adding | Module | Source set |
|---|---|---|
| Shared between mobile **and** server | `core` | `commonMain` |
| Mobile domain / data / model only | `app/shared` | `commonMain` |
| UI components (composables) | `app/shared` | `commonMain` under `ui/` |
| Server-only logic | `server` | `main` |
| Platform-specific implementation | `core` or `app/shared` | `androidMain` / `iosMain` / `jvmMain` |

When in doubt: if the server will never need it, it belongs in `app/shared`. If mobile will never need it, it belongs in `server`. Everything else goes in `core`.

---

## Gotchas

**Read these before touching DI or the module structure.**

- `AppContainer` is for cross-platform dependencies only (lives in `core`). Mobile-specific dependencies — repositories, use-cases — go in `MobileAppContainer` (`app/shared/di/`), not `AppContainer`.
- `MobileAppContainer` holds a reference to `AppContainer` as `appContainer`. Access the shared `httpClient` via `appContainer.httpClient`, not by constructing a new one.
- The `domain/usecase/` directory does not exist yet. Create it when adding the first use-case.
- The `ui/molecules/`, `ui/organisms/`, and `ui/templates/` directories do not exist yet. Create them when first needed.
- `SERVER_PORT` is defined in `core/src/commonMain/kotlin/dev/jakubzika/befair/Constants.kt`. Never hardcode a port number.
- `UserProfile` model lives in `app/shared`, not `core` — it's mobile-only so far. Move it to `core` only when the server needs it.
- Repositories expose state as `StateFlow<T?>` and mutations as `suspend fun`. Do not return raw values from a repository — always back with `MutableStateFlow`.

---

## Recipe 1 — Add a domain model + repository (mobile)

For new tracked entities — e.g. `ClothingItem`, `Tool`, `WashEvent`.

- [ ] **Model**: `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/domain/model/<Name>.kt`
  - Plain `data class`, no Android/iOS imports. Nullable fields use `= null` defaults.
- [ ] **Repository interface**: `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/domain/repository/<Name>Repository.kt`
  - Observable state: `val items: StateFlow<List<Name>>`.
  - Mutations: `suspend fun refresh()`, `suspend fun add(item: Name)`, etc.
- [ ] **Repository implementation**: `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/data/repository/<Name>RepositoryImpl.kt`
  - Constructor-inject `HttpClient` only — get it from `AppContainer`, never construct it directly.
  - Back state with `private val _items = MutableStateFlow<List<Name>>(emptyList())`.
  - Expose as `override val items = _items.asStateFlow()`.
- [ ] **Wire DI** in `MobileAppContainer`:
  ```kotlin
  val <name>Repository: <Name>Repository by lazy {
      <Name>RepositoryImpl(appContainer.httpClient)
  }
  ```
- [ ] **Validate**: `./gradlew :app:shared:testDebugUnitTest`

---

## Recipe 2 — Add a use-case (mobile)

For business logic that orchestrates multiple repositories or enforces domain rules.

- [ ] Create `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/domain/usecase/` if it doesn't exist.
- [ ] **Use-case**: `domain/usecase/<Name>UseCase.kt`
  - Constructor-inject only the repositories it needs.
  - Single entry point: `suspend operator fun invoke(…): Result`.
  - No UI imports, no `HttpClient` directly — delegate to repositories.
- [ ] **Wire DI** in `MobileAppContainer`:
  ```kotlin
  val <name>UseCase: <Name>UseCase by lazy {
      <Name>UseCase(<name>Repository)
  }
  ```
- [ ] **Validate**: `./gradlew :app:shared:testDebugUnitTest`

---

## Recipe 3 — Add a screen (mobile UI)

Follow the [UI atomic design rules](./app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/AGENTS.md).

- [ ] **Template** (layout only, no state): `ui/templates/<Name>Template.kt`
  - All data as parameters. No repository access, no coroutines.
  - Create `ui/templates/` directory if it doesn't exist.
- [ ] **Screen** (data + template): `ui/screens/<Name>Screen.kt`
  - Read state from repository via `CompositionLocals` or a passed-in container reference.
  - Collect `StateFlow` with `collectAsState()`.
  - Pass data down to the template.
- [ ] **Register navigation**: add the new route to the `NavHost` in `App.kt` or the existing navigation setup.
- [ ] **Colors and typography**: always `MaterialTheme.colorScheme.*` and `MaterialTheme.typography.*`. Never hardcode hex values.
- [ ] **Validate**: `./gradlew :app:androidApp:assembleDebug`

---

## Recipe 4 — Add an API endpoint (server)

- [ ] **Route**: add to `Application.module()` in `server/src/main/kotlin/dev/jakubzika/befair/Application.kt`.
  - For more than 2-3 routes on a feature, extract to `fun Application.configure<Feature>()` and call it from `module()`.
- [ ] **Response model**: `@Serializable data class` in `server/src/main/kotlin/dev/jakubzika/befair/`. If mobile will also use it, put it in `core` instead.
- [ ] **Content negotiation**: install `ContentNegotiation` with `json()` if returning JSON (add `ktor-server-content-negotiation` to `libs.versions.toml` when first needed).
- [ ] **Test**: add a `testApplication { }` test in `ApplicationTest.kt`:
  ```kotlin
  @Test
  fun test<Name>() = testApplication {
      application { module() }
      val response = client.get("/api/v1/<path>")
      assertEquals(HttpStatusCode.OK, response.status)
  }
  ```
- [ ] **Validate**: `./gradlew :server:test`

---

## Recipe 5 — Add shared logic to core

Use only when the logic is needed by both mobile and server.

- [ ] **File**: `core/src/commonMain/kotlin/dev/jakubzika/befair/<package>/<Name>.kt`
- [ ] **Platform split** (if needed): add `expect` in `commonMain`, `actual` in `androidMain`, `iosMain`, `jvmMain`. Follow the `HttpClientFactory` pattern.
- [ ] **Wire DI**: add to `AppContainer` as `val <name>: <Name> by lazy { … }`.
- [ ] **Test**: prefer `commonTest`. Add platform-specific tests only when behavior differs per platform.
- [ ] **Validate**: `./gradlew :core:testDebugUnitTest`
