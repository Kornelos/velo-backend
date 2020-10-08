package pl.edu.pw.mini.velobackend.api.authentication

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants.EMAIL_REGEX

@RestController
class RegisterEndpoint(
        val veloUserRepository: VeloUserRepository,
        val passwordEncoder: PasswordEncoder
) {
    @PostMapping("/register")
    fun register(@RequestHeader username: String,
                 @RequestHeader password: String,
                 @RequestHeader firstName: String,
                 @RequestHeader lastName: String): ResponseEntity<HttpStatus> {
        if (!username.matches(EMAIL_REGEX)) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        //TODO: match password
        //require(password.matches(PASSWORD_REGEX))
        if (veloUserRepository.findVeloUserByEmail(username) != null) {
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        veloUserRepository.addVeloUser(VeloUser(
                email = username,
                password = passwordEncoder.encode(password),
                firstName = firstName,
                lastName = lastName
        ))
        return ResponseEntity(HttpStatus.CREATED)
    }
}