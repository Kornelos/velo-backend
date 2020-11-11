package pl.edu.pw.mini.velobackend.api.athlete

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteService
import java.security.Principal
import java.util.UUID

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

    @GetMapping(produces = ["application/json"])
    fun getAthleteById(@RequestParam athleteId: UUID) = athleteService.getAthleteById(athleteId)

    @GetMapping(produces = ["application/json"], value = ["/athletes"])
    fun getAthletesByIds(@RequestParam athleteIds: List<UUID>) = athleteService.getAthletesByIds(athleteIds)
}