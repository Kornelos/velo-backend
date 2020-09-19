package pl.edu.pw.mini.velobackend.api.strava

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.api.strava.utils.WorkoutImportTestData
import pl.edu.pw.mini.velobackend.infrastructure.strava.StravaService
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.StravaUser
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
import pl.edu.pw.mini.velobackend.infrastructure.strava.dao.StravaUserRepositoryMock
import java.time.LocalDateTime
import java.util.UUID

class StravaImportTest : BasicEndpointTest() {
    @Autowired
    lateinit var stravaService: StravaService

    @Autowired
    lateinit var stravaUserRepositoryMock: StravaUserRepositoryMock

    init {
        createStubs()
    }

    val stravaUser = StravaUser(1, TokenPair("accessToken", "refreshToken",
            LocalDateTime.now().plusHours(1)), UUID.randomUUID())


    @Test
    fun `should parse mock data`() {
        //given
        stravaUserRepositoryMock.addStravaUser(stravaUser)
        val response = stravaService.updateWorkoutsFor(stravaUser.athleteId!!,
                after = WorkoutImportTestData.afterEpoch,
                before = WorkoutImportTestData.beforeEpoch
        )

        //when
//        val workouts: JsonArray = Json.parseToJsonElement(response).jsonArray
//        val parsed = workouts.map { Json { ignoreUnknownKeys = true }.decodeFromJsonElement<SummaryActivity>(it) }

        //then
        //parsed.map { it `should be instance of` SummaryActivity::class }
        response `should be equal to` "success"
    }

    private fun createStubs() {
        stubFor(get(urlMatching("/athlete/activities.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(WorkoutImportTestData.activitiesResponse)
                ))

        stubFor(get(urlMatching("/activities/.*/streams.*"))
                .willReturn(aResponse().withTransformers("stream")
                ))
    }
}
