package pl.edu.pw.mini.velobackend.api.authentication

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants.EMAIL_REGEX

@RestController
class PasswordResetEndpoint(
        val veloUserRepository: VeloUserRepository
) {

    @PostMapping("reset-password")
    fun resetPassword(@RequestHeader email: String): String {
        if (!email.matches(EMAIL_REGEX)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong email format")
        }
        val veloUser = veloUserRepository.findVeloUserByEmail(email)
        //TODO: implement proper password reminder with email
        return veloUser?.password
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with such email does not exist")
    }
}