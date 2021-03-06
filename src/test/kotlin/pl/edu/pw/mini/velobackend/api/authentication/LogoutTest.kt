package pl.edu.pw.mini.velobackend.api.authentication

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.infrastructure.security.SecurityConstants
import pl.edu.pw.mini.velobackend.infrastructure.security.dto.JwtTokenRepository

@AutoConfigureMockMvc
class LogoutTest : BasicEndpointTest() {

    @Autowired
    lateinit var jwtTokenRepository: JwtTokenRepository

    @Test
    fun `should log out user`() {
        //given user registered
        registerUser("email@test.com")

        //when user logs in
        val loginResponse = mockMvc.perform(MockMvcRequestBuilders.post(securityProperties.loginUrl)
                .header("Email", "email@test.com")
                .header("Password", "Password123!")
        ).andReturn().response

        val authorization = loginResponse.getHeaderValue(SecurityConstants.TOKEN_HEADER) as String
        val token = authorization.removePrefix("Bearer").trim()
        jwtTokenRepository.tokenExistsByValue(token) `should be equal to` true

        // and when user logs out
        val logoutResponse = mockMvc.perform(MockMvcRequestBuilders.post("/logout-user")
                .header(SecurityConstants.TOKEN_HEADER, authorization)
        ).andReturn().response

        //then
        logoutResponse.status `should be equal to` 200
        jwtTokenRepository.tokenExistsByValue(token) `should be equal to` false
    }
}