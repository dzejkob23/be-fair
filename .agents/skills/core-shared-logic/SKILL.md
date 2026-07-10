---
name: core-shared-logic
description: >
  Add shared logic to the BeFair core module — code consumed by more than one target
  (mobile AND server), including DTOs/models, constants, pure domain logic, or an
  expect/actual platform abstraction. Use only when both mobile and server need the code.
compatibility: Requires Kotlin Multiplatform and the BeFair core module structure.
metadata:
  author: be-fair
  version: "1.0"
---

## When to use

Only when the logic is genuinely needed by **both** mobile and server. In practice the server
consumes just DTOs, models, constants, and pure domain logic from `core` — no Compose, no Ktor
*client*, no secure storage. If you'd have to write a throwaway `actual` for a target that never
uses the code, it's misplaced → put it in `app/shared` or `server` instead.

## Step-by-step

- [ ] **File**: `core/src/commonMain/kotlin/dev/jakubzika/befair/<package>/<Name>.kt`
- [ ] **Platform split** (only if needed): add `expect` in `commonMain`, `actual` in `androidMain`,
  `iosMain`, and `jvmMain`. Keep the `expect` signature minimal — follow the `HttpClientFactory` pattern.
- [ ] **Constants** go in `core/.../Constants.kt` and are referenced by name — never inline magic values.
- [ ] **Wire DI** (if it's a dependency): add to `CoreContainer` as `val <name>: <Name> by lazy { … }`.
  Mobile-only dependencies go in `AppContainer`, not here.
- [ ] **Test**: prefer `commonTest`. Add platform-specific tests only when behavior differs per platform.
- [ ] **Validate**: `./gradlew :core:testDebugUnitTest`
  - All targets compile: `./gradlew :core:compileKotlinAndroid :core:compileKotlinIosArm64 :core:compileKotlinJvm`
