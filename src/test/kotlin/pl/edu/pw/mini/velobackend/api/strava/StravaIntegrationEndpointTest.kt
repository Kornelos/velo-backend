package pl.edu.pw.mini.velobackend.api.strava

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.AuthorizationResponse
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.StravaAthlete
import java.time.Duration
import java.time.LocalDateTime

@AutoConfigureWireMock(port = 27042)
@ActiveProfiles("test")
class StravaIntegrationEndpointTest : BasicEndpointTest() {

    @Test
    fun `should handle Authorization Code from Strava`() {
        //when
        val result = mockMvc.perform(get(
                UriComponentsBuilder.fromUriString("/strava-auth")
                        .queryParam("code", "authorization code")
                        .queryParam("scope", "read,activity:read_all,profile:read_all")
                        .queryParam("state", "")
                        .build().toUri()
        )).andReturn()

        //then
        result.response.contentAsString `should be equal to` "Successful authorizing user with id: 5235"
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupStub() {
            stubFor(post(urlMatching("/oauth/token.*"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(createAuthResponse())))
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