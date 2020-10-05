package pl.edu.pw.mini.velobackend.domain.user

import java.time.Duration
import java.time.Instant
import java.util.UUID

data class ForgottenPasswordToken(
        val id: UUID = UUID.randomUUID(),
        val email: String,
        val expire: Instant = Instant.now().plus(Duration.ofHours(24)),
        val newPassword: String = generateRandomPassword()
)

fun generateRandomPassword(): String {
    val chars = ('0'..'z').toList().toTypedArray()
    return (1..32).map { chars.random() }.joinToString("")
}