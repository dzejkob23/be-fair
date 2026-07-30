package dev.jakubzika.befair.auth

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import kotlinx.serialization.Serializable

@Serializable
data class EmailPayload(
    val from: String,
    val to: List<String>,
    val subject: String,
    val html: String
)

class EmailService(
    private val apiKey: String,
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    },
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
) {

    suspend fun sendEmail(to: String, subject: String, bodyHtml: String): Boolean {
        val response = client.post("https://api.resend.com/emails") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            contentType(ContentType.Application.Json)
            setBody(EmailPayload(
                from = "No Reply <noreply@yourdomain.com>",
                to = listOf(to),
                subject = subject,
                html = bodyHtml
            ))
        }
        return response.status.isSuccess()
    }

    fun sendOtpEmail(email: String, code: String) {
        val bodyHtml = createHTML().html {
            body {
                h1 { +"Your Verification Code" }
                p { +"Please use the following code to complete your registration:" }
                p {
                    style = "font-size: 24px; font-weight: bold; color: #1D3557;"
                    +code
                }
                p { +"This code is valid for 10 minutes." }
            }
        }

        println("========================================")
        println("[OTP EMAIL] to: $email")
        println("[OTP EMAIL] your verification code is: $code")
        println("========================================")

        coroutineScope.launch {
            try {
                val success = sendEmail(email, "Your Verification Code", bodyHtml)
                if (success) {
                    println("[OTP EMAIL] Successfully sent email to $email via Resend.")
                } else {
                    println("[OTP EMAIL] Failed to send email to $email via Resend.")
                }
            } catch (e: Exception) {
                System.err.println("[OTP EMAIL] Error sending email to $email via Resend: ${e.message}")
            }
        }
    }
}
