package pl.edu.pw.mini.velobackend.api.strava

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest

class StravaIntegrationEndpointTest : BasicEndpointTest() {

    val stravaAuthUri = "/strava/auth"

    @Test
    fun `should handle Authorization Code from Strava`() {
        //when

        val result = mockMvc.perform(get(
                UriComponentsBuilder.fromUriString(stravaAuthUri)
                        .queryParam("code", "authorization code")
                        .queryParam("scope", "read,activity:read_all,profile:read_all")
                        .queryParam("state", "")
                        .build().toUri()
        )).andReturn()

        //then
        result.response.contentAsString `should contain` "User UUID"
    }

    @Test
    fun `should not accept Authorization with wrong scope`() {
        //when
        val result = mockMvc.perform(get(
                UriComponentsBuilder.fromUriString(stravaAuthUri)
                        .queryParam("code", "authorization code")
                        .queryParam("scope", "activity:read_all")
                        .queryParam("state", "")
                        .build().toUri()
        )).andReturn()
        //then
        result.response.status `should be equal to` HttpStatus.BAD_REQUEST.value()
    }


}