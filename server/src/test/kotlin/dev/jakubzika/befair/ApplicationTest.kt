package dev.jakubzika.befair

import dev.jakubzika.befair.auth.EmailService
import dev.jakubzika.befair.domain.model.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Ktor: ${Greeting().greet()}", response.bodyAsText())
    }

    @Test
    fun testRegisterUserSendsEmailAsynchronouslyAndReturnsOk() = testApplication {
        val mockEmailService = EmailService(
            apiKey = "re_test_key",
            client = HttpClient(MockEngine { respondOk() })
        )
        application {
            module(mockEmailService)
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val uniqueEmail = "user_${System.currentTimeMillis()}@example.com"
        val response = client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest(
                email = uniqueEmail,
                name = "New User",
                password = "securepassword123"
            ))
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val bodyText = response.bodyAsText()
        assertTrue(bodyText.contains("Verification code sent to $uniqueEmail"))
    }
}
