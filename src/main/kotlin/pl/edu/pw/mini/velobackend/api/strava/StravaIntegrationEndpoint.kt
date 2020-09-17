package pl.edu.pw.mini.velobackend.api.strava

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.infrastructure.strava.StravaAuthService
import pl.edu.pw.mini.velobackend.infrastructure.strava.StravaUser
import java.time.Instant
import java.util.UUID


@RestController
@RequestMapping("/strava")
class StravaIntegrationEndpoint(val stravaAuthService: StravaAuthService) {

    @GetMapping("/auth")
    fun authenticationRedirect(@RequestParam code: String, @RequestParam scope: String, @RequestParam state: String): String {
        //TODO: do not expose strava user in controller
        check(scope.contains("profile:read_all").and(scope.contains("activity:read_all")))
        val stravaUser: StravaUser = stravaAuthService.createStravaUser(code, scope, state)
        return "User UUID: ${stravaUser.athleteId}"
    }

    @PostMapping("/import")
    fun syncWorkouts(@RequestParam athleteId: UUID, @RequestParam beforeEpoch: Long, @RequestParam afterEpoch: Long): String {
        return stravaAuthService.updateWorkoutsFor(athleteId, Instant.ofEpochSecond(beforeEpoch), Instant.ofEpochSecond(afterEpoch))
    }

    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateHandler(): ResponseEntity<String> = ResponseEntity("Illegal state", HttpStatus.BAD_REQUEST)


}