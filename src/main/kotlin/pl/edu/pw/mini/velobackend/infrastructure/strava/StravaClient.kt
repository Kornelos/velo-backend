package pl.edu.pw.mini.velobackend.infrastructure.strava

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.infrastructure.configuration.StravaProperties
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.RefreshToken
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
import pl.edu.pw.mini.velobackend.infrastructure.strava.dao.StravaUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.AuthorizationResponse
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.SummaryActivity
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams.StreamSet
import java.net.URI
import java.time.Instant
import java.time.LocalDateTime


@Component
class StravaClient(
        val stravaProperties: StravaProperties,
        val restTemplate: RestTemplate,
        val stravaUserRepository: StravaUserRepository,
) {
    val jsonParser = Json { ignoreUnknownKeys = true }
    fun exchangeCodeForAccessToken(code: String): AuthorizationResponse {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(stravaProperties.tokenUrl)
                .queryParam("client_id", stravaProperties.clientId)
                .queryParam("client_secret", stravaProperties.clientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
        val response = restTemplate.postForEntity(uriBuilder.toUriString(), null, String::class.java)
        return Json.decodeFromString(response.body.orEmpty()) //hack
    }

    fun getWorkoutsForStravaUser(tokenPair: TokenPair, beforeEpoch: Instant, afterEpoch: Instant): List<SummaryActivity> {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(stravaProperties.apiUrl + "/athlete/activities")
                .queryParam("before", beforeEpoch.epochSecond)
                .queryParam("after", afterEpoch.epochSecond)

        val response = makeRequest(tokenPair, uriBuilder.build().toUri(), HttpMethod.GET)
        val parsedResponse: JsonArray = response.body?.let { jsonParser.parseToJsonElement(it).jsonArray }
                ?: JsonArray(emptyList())
        return parsedResponse.map { jsonParser.decodeFromJsonElement(it) }
    }

    fun getWorkoutStreams(tokenPair: TokenPair, summaryActivity: SummaryActivity): StreamSet? {
        //time, distance, latlng, altitude, velocity_smooth, heartrate, cadence, watts, temp, moving, grade_smooth
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(stravaProperties.apiUrl + "/activities/${summaryActivity.id}/streams")
                .queryParam("keys", "time,distance,latlng,altitude,velocity_smooth,heartrate,cadence,watts")
                .queryParam("key_by_type", true)

        val response = makeRequest(tokenPair, uriBuilder.build().toUri(), HttpMethod.GET)
        val jsonElement: JsonElement = response.body.let { jsonParser.parseToJsonElement(it!!) }
        return jsonParser.decodeFromJsonElement<StreamSet>(jsonElement)
    }

    private fun makeRequest(tokenPair: TokenPair, uri: URI, method: HttpMethod): ResponseEntity<String> {
        val checkedPair = checkToken(tokenPair)
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer ${checkedPair.accessToken}")
        val request = HttpEntity<String>(headers)
        return restTemplate.exchange(uri, method, request, String::class.java)
    }

    private fun checkToken(tokenPair: TokenPair): TokenPair {
        return if (tokenPair.expirationDate < LocalDateTime.now()) {
            val pair = exchangeRefreshToken(tokenPair.refreshToken)
            stravaUserRepository.updateTokenPair(tokenPair, pair)
            pair
        } else {
            tokenPair
        }

    }

    private fun exchangeRefreshToken(refreshToken: RefreshToken): TokenPair {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(stravaProperties.tokenUrl)
                .queryParam("client_id", stravaProperties.clientId)
                .queryParam("client_secret", stravaProperties.clientSecret)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", refreshToken)
        val response = restTemplate.postForEntity(uriBuilder.toUriString(), null, String::class.java)
        val authResponse: AuthorizationResponse = Json.decodeFromString(response.body.orEmpty())
        return TokenPair(authResponse.accessToken, authResponse.refreshToken, authResponse.expiresAt)
    }
}