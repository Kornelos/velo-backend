package pl.edu.pw.mini.velobackend.api

import com.github.tomakehurst.wiremock.client.WireMock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import pl.edu.pw.mini.velobackend.infrastructure.configuration.SecurityProperties
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.AuthorizationResponse
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.StravaAthlete
import pl.edu.pw.mini.velobackend.infrastructure.user.dto.VeloUserDto
import pl.edu.pw.mini.velobackend.infrastructure.workout.WorkoutDto
import pl.edu.pw.mini.velobackend.wiremock.WiremockConfig
import java.time.Duration
import java.time.LocalDateTime


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
@Import(WiremockConfig::class)
@EnableConfigurationProperties(SecurityProperties::class)
abstract class BasicEndpointTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Autowired
    lateinit var securityProperties: SecurityProperties

    @AfterEach
    fun cleanMongo() {
        mongoTemplate.dropCollection(VeloUserDto::class.java)
        mongoTemplate.dropCollection(WorkoutDto::class.java)
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupAuthStub() {
            WireMock.stubFor(WireMock.post(WireMock.urlMatching("/oauth/token.*"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(createAuthResponse())))

            WireMock.stubFor(WireMock.post(WireMock.urlMatching("/recaptcha/api/siteverify.*"))
                    .willReturn(WireMock.aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("""{"success": true}""")))
        }


        private fun createAuthResponse(): String {
            val authorizationResponse = AuthorizationResponse("Bearer",
                    LocalDateTime.now().plusHours(6),
                    Duration.ofHours(6),
                    "refresh_token",
                    "some access token",
                    StravaAthlete(5235, "user", 2, "test", "user",
                            "a", "b", "c", "M", premium = true, summit = true,
                            createdAt = "2012-06-16T14:55:39Z", updatedAt = "2012-06-16T14:55:39Z", badgeTypeId = 0,
                            profileMedium = null, profile = null, friend = null, follower = null)
            )
            return Json.encodeToString(authorizationResponse)
        }
    }
}