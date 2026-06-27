package dev.jakubzika.befair.auth

/**
 * Mock email service. There is no SMTP integration: the OTP is printed to the server
 * console so the flow can be tested locally. Swap the body for a real provider later.
 */
object EmailService {

    fun sendOtpEmail(email: String, code: String) {
        println("========================================")
        println("[OTP EMAIL] to: $email")
        println("[OTP EMAIL] your verification code is: $code")
        println("========================================")
    }
}
