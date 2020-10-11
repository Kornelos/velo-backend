package pl.edu.pw.mini.velobackend.infrastructure.user.dto

import org.springframework.data.annotation.Id
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordToken
import java.time.Instant
import java.util.UUID

data class ForgottenPasswordTokenDto(
        @Id val id: UUID,
        val email: String,
        val expire: Instant,
        val newPassword: String
) {
    fun toForgottenPasswordToken() = ForgottenPasswordToken(id, email, expire, newPassword)
}

fun ForgottenPasswordToken.toForgottenPasswordTokenDto() = ForgottenPasswordTokenDto(id, email, expire, newPassword)