package pl.edu.pw.mini.velobackend.api.strava

import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpServerErrorException
import pl.edu.pw.mini.velobackend.infrastructure.strava.StravaService
import pl.edu.pw.mini.velobackend.infrastructure.workout.toWorkoutMeta
import java.time.Instant
import java.util.UUID


@RestController
@RequestMapping("/strava")
@Tag(name = "Strava Integration")
class StravaIntegrationEndpoint(val stravaService: StravaService) {

    @GetMapping("/auth")
    fun authenticationRedirect(@RequestParam code: String, @RequestParam scope: String, @RequestParam state: String): String {
        check(scope.contains("profile:read_all").and(scope.contains("activity:read_all")))
        stravaService.createStravaUser(code, scope, state)
        return "<body onload='window.close();'/>"
    }

    @PostMapping("/import", produces = ["application/json"])
    fun syncWorkouts(@RequestParam athleteId: UUID, @RequestParam beforeEpoch: Long, @RequestParam afterEpoch: Long): String {
        val workouts = stravaService.updateWorkoutsFor(athleteId,
                Instant.ofEpochSecond(beforeEpoch),
                Instant.ofEpochSecond(afterEpoch)
        )
        return Json.encodeToString(workouts.map { it.toWorkoutMeta() })
    }

    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateHandler(): ResponseEntity<String> = ResponseEntity("Illegal state", HttpStatus.BAD_REQUEST)

    @ExceptionHandler(HttpServerErrorException.InternalServerError::class)
    fun stravaErrorHandler(): ResponseEntity<String> = ResponseEntity("Strava is not responding", HttpStatus.FAILED_DEPENDENCY)
}