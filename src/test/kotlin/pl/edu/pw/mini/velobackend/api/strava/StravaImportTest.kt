package pl.edu.pw.mini.velobackend.api.strava

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.anyUrl
import com.github.tomakehurst.wiremock.client.WireMock.removeStub
import com.github.tomakehurst.wiremock.client.WireMock.serverError
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.api.strava.utils.WorkoutImportTestData
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.StravaUser
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
import pl.edu.pw.mini.velobackend.infrastructure.strava.dao.StravaUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.strava.dto.StravaUserDto
import pl.edu.pw.mini.velobackend.infrastructure.workout.WorkoutDto
import java.time.LocalDateTime
import java.util.UUID

class StravaImportTest : BasicEndpointTest() {

    @Autowired
    lateinit var stravaUserRepository: StravaUserRepository

    @BeforeEach
    fun beforeEach() {
        createStubs()
        mongoTemplate.dropCollection(StravaUserDto::class.java)
        mongoTemplate.dropCollection(WorkoutDto::class.java)
    }

    val stravaUser = StravaUser(1, TokenPair("accessToken", "refreshToken",
            LocalDateTime.now().plusHours(1)), UUID.randomUUID())


    @Test
    fun `should get workout list and download workouts from the list`() {
        //given
        stravaUserRepository.addStravaUser(stravaUser)

        //when
        val response = mockMvc.perform(MockMvcRequestBuilders.post(UriComponentsBuilder.fromUriString("/strava/import")
                .queryParam("athleteId", stravaUser.athleteId.toString())
                .queryParam("beforeEpoch", WorkoutImportTestData.beforeEpoch.epochSecond)
                .queryParam("afterEpoch", WorkoutImportTestData.afterEpoch.epochSecond)
                .build().toUri()
        )).andReturn().response

        //then
        Json.parseToJsonElement(response.contentAsString).jsonArray.count() `should be equal to` 4
    }

    @Test
    fun `should not fail when strava is down (500)`() {
        //given
        val failureStub = stubFor(WireMock.get(anyUrl()).willReturn(serverError()))
        stravaUserRepository.addStravaUser(stravaUser)

        //when
        val response = mockMvc.perform(MockMvcRequestBuilders.post(UriComponentsBuilder.fromUriString("/strava/import")
                .queryParam("athleteId", stravaUser.athleteId.toString())
                .queryParam("beforeEpoch", "16000000")
                .queryParam("afterEpoch", "15000000")
                .build().toUri()
        )).andReturn().response

        //then
        response.status `should be equal to` HttpStatus.FAILED_DEPENDENCY.value()

        //cleanup
        removeStub(failureStub)
    }

    @Test
    fun `should use refresh token if access token is expired`() {
        //given
        val expiredToken = "ExpiredAccessToken"
        val expiredTokenUser = StravaUser(1, TokenPair(expiredToken, "refreshToken",
                LocalDateTime.now().minusHours(1)), UUID.randomUUID())
        stravaUserRepository.addStravaUser(expiredTokenUser)

        //when
        mockMvc.perform(MockMvcRequestBuilders.post(UriComponentsBuilder.fromUriString("/strava/import")
                .queryParam("athleteId", expiredTokenUser.athleteId.toString())
                .queryParam("beforeEpoch", "16000000")
                .queryParam("afterEpoch", "15000000")
                .build().toUri()
        ))

        //then
        stravaUserRepository.getStravaUserById(expiredTokenUser.id)!!.tokenPair `should not be equal to` expiredTokenUser.tokenPair
    }


    private fun createStubs() {
        stubFor(WireMock.get(urlMatching("/athlete/activities.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(WorkoutImportTestData.activitiesResponse)
                ))

        stubFor(WireMock.get(urlMatching("/activities/.*/streams.*"))
                .willReturn(aResponse().withTransformers("stream")
                ))
    }
}
