package pl.edu.pw.mini.velobackend.api.strava

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.infrastructure.strava.StravaAuthService
import pl.edu.pw.mini.velobackend.infrastructure.strava.StravaUser


@RestController
class StravaIntegrationEndpoint(val stravaAuthService: StravaAuthService) {

    @GetMapping("/strava-auth")
    fun authenticationRedirect(@RequestParam code: String, @RequestParam scope: String, @RequestParam state: String): String {
        //TODO: do not expose strava user in controller
        val stravaUser: StravaUser = stravaAuthService.createStravaUser(code, scope, state)
        return "Successful authorizing user with id: ${stravaUser.id}"
    }
}