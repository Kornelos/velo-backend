package pl.edu.pw.mini.velobackend.api.athlete

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteService
import java.security.Principal

@RestController
@RequestMapping("/athlete")
class AthleteEndpoint(
        val athleteService: AthleteService
) {

    @PostMapping("/add-coach")
    fun addCoach(@RequestHeader coachEmail: String, principal: Principal) {
        val athleteEmail = principal.name
        athleteService.addAthleteForUserWithEmail(coachEmail, athleteEmail)
    }
}