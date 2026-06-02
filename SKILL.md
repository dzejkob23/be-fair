# SKILL.md

Common agent workflow recipes for the BeFair project.
Each recipe is a minimal, ordered checklist. Follow it top-to-bottom and verify with the Safe Change Checklist in [`AGENTS.md`](./AGENTS.md) before finishing.

---

## Recipe 1 — Add a new domain model + repository (mobile)

Use this when adding a new tracked entity (e.g. `ClothingItem`, `Tool`).

1. **Model** — create `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/domain/model/<Name>.kt`
   - Plain `data class`, no Android/iOS imports.
2. **Repository interface** — create `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/domain/repository/<Name>Repository.kt`
   - Return types use `StateFlow<T?>` for observable state; `suspend fun` for mutations.
3. **Repository implementation** — create `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/data/repository/<Name>RepositoryImpl.kt`
   - Inject `HttpClient` from `AppContainer` via constructor.
   - Back with `MutableStateFlow`; expose as `StateFlow`.
4. **Wire DI** — add a `val <name>Repository: <Name>Repository by lazy { <Name>RepositoryImpl(appContainer.httpClient) }` to `MobileAppContainer`.
5. **Test** — add a unit test in `app/shared/src/commonTest/`.
6. **Build check** — `./gradlew :app:shared:testDebugUnitTest`

---

## Recipe 2 — Add a new screen (mobile UI)

Use this when adding a new destination to the bottom nav or a drill-down screen.

1. **Template** (if needed) — create `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/templates/<Name>Template.kt`
   - Layout only; receive all data as parameters; no ViewModel/state inside.
2. **Screen** — create `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/screens/<Name>Screen.kt`
   - Reads state from the repository (via `CompositionLocals` or passed-in container).
   - Calls the template with real data.
3. **Navigation** — register the new screen in the app navigation graph (find existing `NavHost` in `App.kt` or navigation setup file).
4. **UI guidance** — follow atomic design rules in [`app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/AGENTS.md`](./app/shared/src/commonMain/kotlin/dev/jakubzika/befair/ui/AGENTS.md).
5. **Build check** — `./gradlew :app:androidApp:assembleDebug`

---

## Recipe 3 — Add a new API endpoint (server)

Use this when exposing new server functionality.

1. **Route** — add a new `route("/path") { get/post/put/delete { … } }` block inside `Application.module()` in `server/src/main/kotlin/dev/jakubzika/befair/Application.kt`.
   - For non-trivial sets of routes, extract to a separate `fun Application.configure<Feature>()` extension and call it from `module()`.
2. **Response model** — define a `@Serializable` data class in `server/src/main/kotlin/dev/jakubzika/befair/` (or `core` if shared with mobile).
3. **Database** (if needed) — define an Exposed `Table` object and add a migration/schema creation call. See [`server/AGENTS.md`](./server/AGENTS.md) for conventions.
4. **Test** — add a `testApplication { … }` test in `server/src/test/kotlin/dev/jakubzika/befair/ApplicationTest.kt`.
5. **Build check** — `./gradlew :server:test`

---

## Recipe 4 — Add shared logic to core

Use this when adding something needed by both mobile and server (e.g. a shared model, a network utility).

1. **File location** — `core/src/commonMain/kotlin/dev/jakubzika/befair/<package>/<Name>.kt`
2. **Platform splits** — if you need a platform-specific implementation, add an `expect` declaration in `commonMain` and `actual` implementations in `androidMain`, `iosMain`, and `jvmMain`. Follow the `HttpClientFactory` pattern.
3. **DI** — if the new component needs to be injected, add it to `AppContainer` as a `by lazy` property.
4. **Test** — prefer `commonTest`; add platform-specific tests only if behavior differs.
5. **Build check** — `./gradlew :core:testDebugUnitTest`

---

## Recipe 5 — Add a new use-case (mobile)

Use this when extracting business logic that orchestrates multiple repositories.

1. **Use-case class** — create `app/shared/src/commonMain/kotlin/dev/jakubzika/befair/domain/usecase/<Name>UseCase.kt`
   - Constructor-inject repositories it needs.
   - Single public `suspend operator fun invoke(…)` entry point.
2. **Wire DI** — add to `MobileAppContainer` as a `by lazy` property.
3. **Consume in Screen** — call from the screen composable's coroutine scope or a ViewModel.
4. **Build check** — `./gradlew :app:shared:testDebugUnitTest`
