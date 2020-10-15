package pl.edu.pw.mini.velobackend.api.authentication

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.infrastructure.security.dto.JwtTokenRepository

@RestController
class LogoutEndpoint(
        val jwtTokenRepository: JwtTokenRepository
) {

    @PostMapping("/logout-user")
    fun logoutUser(@RequestHeader authorization: String) {
        jwtTokenRepository.deleteTokenByValue(authorization.removePrefix("Bearer").trim())
    }
}