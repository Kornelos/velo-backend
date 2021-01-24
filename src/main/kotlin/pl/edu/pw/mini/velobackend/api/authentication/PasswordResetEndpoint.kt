package pl.edu.pw.mini.velobackend.api.authentication

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordTokenRepository
import pl.edu.pw.mini.velobackend.domain.user.PasswordResetSender
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants.EMAIL_REGEX
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants.PASSWORD_REGEX
import java.util.UUID

@RestController
@Tag(name = "Password Reset")
class PasswordResetEndpoint(
        val veloUserRepository: VeloUserRepository,
        val forgottenPasswordTokenRepository: ForgottenPasswordTokenRepository,
        val passwordResetSender: PasswordResetSender,
        val passwordEncoder: PasswordEncoder,
) {

    @PostMapping("/reset-password")
    fun resetPassword(@RequestHeader email: String) {
        if (!email.matches(EMAIL_REGEX)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong email format")
        }
        val veloUser = veloUserRepository.getVeloUserByEmail(email)
        if (veloUser != null) {
            passwordResetSender.sendPasswordResetMessage(email, veloUser.firstName)
        }
    }

    @PostMapping("/new-password")
    fun changeForgottenPassword(@RequestHeader tokenId: UUID, @RequestHeader newPassword: String) {
        if (!newPassword.matches(PASSWORD_REGEX)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide valid password")
        }
        val token = forgottenPasswordTokenRepository.getTokenById(tokenId)
        if (token != null) {
            veloUserRepository.changePasswordForVeloUserWithEmail(token.email, passwordEncoder.encode(newPassword))
        } else {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }
}