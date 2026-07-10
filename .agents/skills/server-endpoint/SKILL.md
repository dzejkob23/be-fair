---
name: server-endpoint
description: >
  Add or modify a Ktor API endpoint in the BeFair server module. Use when adding a route,
  request/response model, content negotiation, or a testApplication test to the JVM backend.
compatibility: Requires the BeFair server (Ktor/Netty) module and Gradle.
metadata:
  author: be-fair
  version: "1.0"
---

## When to use

- Adding a new HTTP route to the Ktor server.
- Adding a request/response model, serialization, or an endpoint test.

## Step-by-step

- [ ] **Route**: add to `Application.module()` in `server/src/main/kotlin/dev/jakubzika/befair/Application.kt`.
  - For more than 2–3 routes on a feature, extract to `fun Application.configure<Feature>()` and call it from `module()`.
- [ ] **Model**: `@Serializable data class` in `server/src/main/kotlin/dev/jakubzika/befair/` (or a `model/` package).
  - If mobile will also use it, put it in `core` instead (see the `core-shared-logic` skill).
- [ ] **Content negotiation**: install `ContentNegotiation` with `json()` if returning JSON
  (add `ktor-server-content-negotiation` to `gradle/libs.versions.toml` when first needed).
- [ ] **Never hardcode the port** — it comes from `SERVER_PORT` in `core/Constants.kt`.
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

If touching Exposed/H2 persistence, follow the DB conventions in `server/AGENTS.md`.
