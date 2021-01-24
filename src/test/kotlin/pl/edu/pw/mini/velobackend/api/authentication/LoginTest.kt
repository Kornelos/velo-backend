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
        registerUser("email@test.com")

        //when
        val response = mockMvc.perform(post(securityProperties.loginUrl)
                .header("Email", "email@test.com")
                .header("Password", "Password123!")
        ).andReturn().response

        //then
        response.getHeaderValue(SecurityConstants.TOKEN_HEADER).toString() `should contain` SecurityConstants.TOKEN_PREFIX
    }
}