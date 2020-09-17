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
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.infrastructure.configuration.StravaProperties
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.AuthorizationResponse
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.SummaryActivity
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams.StreamSet
import java.time.Instant


@Component
class StravaClient(val stravaProperties: StravaProperties,
                   val restTemplate: RestTemplate
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

    fun getWorkoutsForStravaUser(accessToken: String, beforeEpoch: Instant, afterEpoch: Instant): List<SummaryActivity> {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(stravaProperties.apiUrl + "/athlete/activities")
                .queryParam("before", beforeEpoch.epochSecond)
                .queryParam("after", afterEpoch.epochSecond)

        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")

        val request = HttpEntity<String>(headers)
        val response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String::class.java)
        val parsedResponse: JsonArray = response.body?.let { jsonParser.parseToJsonElement(it).jsonArray }
                ?: JsonArray(emptyList())
        return parsedResponse.map { jsonParser.decodeFromJsonElement(it) }
    }

    fun getWorkoutStreams(summaryActivity: SummaryActivity, accessToken: String): StreamSet? {
        //time, distance, latlng, altitude, velocity_smooth, heartrate, cadence, watts, temp, moving, grade_smooth
        val keys = arrayOf("time", "distance", "latlng", "altitude", "velocity_smooth", "heartrate", "cadence", "watts")
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(stravaProperties.apiUrl + "/activities/${summaryActivity.id}/streams")
                .queryParam("keys", "time, distance, latlng, altitude, velocity_smooth, heartrate, cadence, watts")
                .queryParam("key_by_type", true)
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")
        val request = HttpEntity<String>(headers)

        val response = restTemplate.exchange(uriBuilder.build().toUri(), HttpMethod.GET, request, String::class.java)
        val jsonElement: JsonElement = response.body.let { jsonParser.parseToJsonElement(it!!) }
        return jsonParser.decodeFromJsonElement<StreamSet>(jsonElement)
    }

}