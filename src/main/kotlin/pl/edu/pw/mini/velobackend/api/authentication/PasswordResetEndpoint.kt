package pl.edu.pw.mini.velobackend.api.authentication

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordTokenRepository
import pl.edu.pw.mini.velobackend.domain.user.PasswordResetSender
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants.EMAIL_REGEX
import java.util.UUID

@RestController
class PasswordResetEndpoint(
        val veloUserRepository: VeloUserRepository,
        val forgottenPasswordTokenRepository: ForgottenPasswordTokenRepository,
        val passwordResetSender: PasswordResetSender
) {

    @PostMapping("/reset-password")
    fun resetPassword(@RequestHeader email: String) {
        if (!email.matches(EMAIL_REGEX)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong email format")
        }
        val veloUser = veloUserRepository.findVeloUserByEmail(email)
        if (veloUser != null) {
            passwordResetSender.sendPasswordResetMessage(email, veloUser.firstName)
        }
    }

    @GetMapping("/confirm-password")
    fun changeForgottenPassword(@RequestParam tokenId: UUID): String {
        val token = forgottenPasswordTokenRepository.getTokenById(tokenId)
        if (token != null) {
            veloUserRepository.changePasswordForVeloUserWithEmail(token.email, token.newPassword)
        } else {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
        return token.newPassword
    }
}