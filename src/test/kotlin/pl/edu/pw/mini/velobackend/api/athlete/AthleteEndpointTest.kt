package pl.edu.pw.mini.velobackend.api.athlete

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.amshove.kluent.`should contain`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteRepository
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants

@AutoConfigureMockMvc
class AthleteEndpointTest : BasicEndpointTest() {

    @Autowired
    lateinit var userRepository: VeloUserRepository

    @Autowired
    lateinit var athleteRepository: AthleteRepository

    @Test
    fun `should add athlete for coach`() {
        //given
        registerUser("coach@test.com")
        registerUser("athlete@test.com")

        addStrava("athlete@test.com")
        val auth = login("athlete@test.com")

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/athlete/add-coach")
                .header(SecurityConstants.TOKEN_HEADER, auth)
                .header("coachEmail", "coach@test.com")
        ).andExpect(MockMvcResultMatchers.status().isOk)

        val coach = userRepository.getVeloUserByEmail("coach@test.com")!!
        val athlete = athleteRepository.getAthleteByEmail("athlete@test.com")!!

        //then
        coach.athleteUUIDs `should contain` athlete.id
    }

    @Test
    fun `should return athlete for id`() {
        //given
        registerUser("athlete@test.com")
        addStrava("athlete@test.com")
        val auth = login("athlete@test.com")
        val athlete = athleteRepository.getAthleteByEmail("athlete@test.com")
        //when

        val result = mockMvc.perform(MockMvcRequestBuilders.get("/athlete")
                .header(SecurityConstants.TOKEN_HEADER, auth)
                .param("athleteId", athlete?.id.toString())
        ).andReturn().response

        //then
        Json.parseToJsonElement(result.contentAsString).jsonObject.keys `should contain` "id"
    }

    private fun addStrava(email: String) {
        mockMvc.perform(MockMvcRequestBuilders.get(
                UriComponentsBuilder.fromUriString("/strava/auth")
                        .queryParam("code", "authorization code")
                        .queryParam("scope", "read,activity:read_all,profile:read_all")
                        .queryParam("state", "user_email:$email")
                        .build().toUri()
        )).andReturn()
    }

    private fun login(email: String) = mockMvc.perform(MockMvcRequestBuilders.post(securityProperties.loginUrl)
            .header("email", email)
            .header("Password", "pass")
    ).andReturn().response.getHeader(SecurityConstants.TOKEN_HEADER)!!

}