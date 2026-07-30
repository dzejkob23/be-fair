package dev.jakubzika.befair.auth

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EmailServiceTest {

    @Test
    fun testSendEmailConstructsCorrectRequest() = runTest {
        var recordedRequestUrl: String? = null
        var recordedAuthHeader: String? = null
        var recordedBody: EmailPayload? = null

        val mockEngine = MockEngine { request ->
            recordedRequestUrl = request.url.toString()
            recordedAuthHeader = request.headers.get(HttpHeaders.Authorization)

            val bodyContent = request.body
            val contentString = if (bodyContent is OutgoingContent.ByteArrayContent) {
                bodyContent.bytes().decodeToString()
            } else {
                ""
            }
            recordedBody = Json.decodeFromString<EmailPayload>(contentString)

            respond(
                content = """{"id": "test_id"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) { json() }
        }

        val emailService = EmailService(
            apiKey = "re_test_12345",
            client = httpClient
        )

        val success = emailService.sendEmail(
            to = "test@example.com",
            subject = "Hello Test",
            bodyHtml = "<h1>Test</h1>"
        )

        assertTrue(success)
        assertEquals("https://api.resend.com/emails", recordedRequestUrl)
        assertEquals("Bearer re_test_12345", recordedAuthHeader)
        assertEquals("No Reply <noreply@yourdomain.com>", recordedBody?.from)
        assertEquals(listOf("test@example.com"), recordedBody?.to)
        assertEquals("Hello Test", recordedBody?.subject)
        assertEquals("<h1>Test</h1>", recordedBody?.html)
    }
}
