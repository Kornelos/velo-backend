package pl.edu.pw.mini.velobackend.api.authentication

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.domain.athlete.Athlete
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteRepository
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserCreator
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.security.CaptchaService
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants.EMAIL_REGEX
import java.util.UUID

@RestController
@Tag(name = "Register")
class RegisterEndpoint(
        val veloUserRepository: VeloUserRepository,
        val veloUserCreator: VeloUserCreator,
        val captchaService: CaptchaService
) {
    @PostMapping("/register")
    fun register(
            @RequestHeader email: String,
            @RequestHeader password: String,
            @RequestHeader firstName: String,
            @RequestHeader lastName: String,
            @RequestHeader captcha: String
    ): ResponseEntity<HttpStatus> {
        if (!email.matches(EMAIL_REGEX)) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        if(!captchaService.validateNotRobot(captcha)){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        //TODO: match password
        //require(password.matches(PASSWORD_REGEX))
        if (veloUserRepository.getVeloUserByEmail(email) != null) {
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        veloUserCreator.createUser(email, password, firstName, lastName)
        return ResponseEntity(HttpStatus.CREATED)
    }


}