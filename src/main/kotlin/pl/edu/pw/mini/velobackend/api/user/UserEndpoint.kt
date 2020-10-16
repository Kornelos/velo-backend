package pl.edu.pw.mini.velobackend.api.user

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository

@RestController
@Tag(name = "User")
class UserEndpoint(
        val veloUserRepository: VeloUserRepository
) {

    @GetMapping("/user", produces = ["application/json"])
    fun getUser(@RequestParam email: String): UserData? {
        return veloUserRepository.findVeloUserByEmail(email)?.toUserData()
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist")
    }
}