package pl.edu.pw.mini.velobackend.api.authentication

import org.amshove.kluent.`should contain`
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants

@AutoConfigureMockMvc
class LoginTest : BasicEndpointTest() {

    @Test
    fun `should log in registered user`() {
        //given
        mockMvc.perform(post("/register")
                .header("Username", "email@test.com")
                .header("Password", "pass")
                .header("firstName", "first")
                .header("lastName", "last")
        ).andExpect(status().isCreated)

        //when
        val response = mockMvc.perform(post(SecurityConstants.AUTH_LOGIN_URL)
                .header("Username", "email@test.com")
                .header("Password", "pass")
        ).andReturn().response

        //then
        response.getHeaderValue(SecurityConstants.TOKEN_HEADER).toString() `should contain` SecurityConstants.TOKEN_PREFIX
    }
}