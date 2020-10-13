package pl.edu.pw.mini.velobackend.api.strava

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository

class StravaIntegrationEndpointTest : BasicEndpointTest() {

    @Autowired
    lateinit var veloUserRepository: VeloUserRepository

    val stravaAuthUri = "/strava/auth"

    @Test
    fun `should handle Authorization Code from Strava`() {
        //given
        val userEmail = "email@test.com"
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .header("Username", userEmail)
                .header("Password", "pass")
                .header("firstName", "first")
                .header("lastName", "last")
        ).andExpect(MockMvcResultMatchers.status().isCreated)

        //when

        val result = mockMvc.perform(get(
                UriComponentsBuilder.fromUriString(stravaAuthUri)
                        .queryParam("code", "authorization code")
                        .queryParam("scope", "read,activity:read_all,profile:read_all")
                        .queryParam("state", "user_email:${userEmail}")
                        .build().toUri()
        )).andReturn()

        //then
        result.response.contentAsString `should contain` "User UUID"
        veloUserRepository.findVeloUserByEmail(userEmail)!!.isStravaConnected `should be equal to` true
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