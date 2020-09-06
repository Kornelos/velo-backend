package pl.edu.pw.mini.velobackend.infrastructure.strava

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.AuthorizationResponse

@Service
@EnableConfigurationProperties(StravaConfiguration::class)
class StravaAuthService(val restTemplate: RestTemplate, val stravaConfiguration: StravaConfiguration) {

    fun createStravaUser(code: String, scope: String, state: String): StravaUser {
        val authorizationResponse = exchangeCodeForAccessToken(code)

        val stravaUser = StravaUser(authorizationResponse.athlete.id,
                authorizationResponse.accessToken,
                authorizationResponse.refreshToken,
                authorizationResponse.expiresAt,
                null)
        //TODO: add him to the repository
        return stravaUser
    }

    private fun exchangeCodeForAccessToken(code: String): AuthorizationResponse {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(stravaConfiguration.tokenUrl)
                .queryParam("client_id", stravaConfiguration.clientId)
                .queryParam("client_secret", stravaConfiguration.clientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
        val response = restTemplate.postForEntity(uriBuilder.toUriString(), null, String::class.java)
        return Json.decodeFromString(response.body.orEmpty()) //hack
    }
}

