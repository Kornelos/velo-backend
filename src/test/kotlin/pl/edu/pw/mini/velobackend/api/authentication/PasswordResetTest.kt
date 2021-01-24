package pl.edu.pw.mini.velobackend.api.authentication

import org.amshove.kluent.`should not be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordTokenRepository
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository

@AutoConfigureMockMvc
class PasswordResetTest : BasicEndpointTest() {

    @Autowired
    lateinit var forgottenPasswordTokenRepository: ForgottenPasswordTokenRepository

    @Autowired
    lateinit var userRepository: VeloUserRepository

    @Test
    fun `should reset user password`() {
        //given user is registered
        val email = "email@test.com"
        registerUser(email)
        val user = userRepository.getVeloUserByEmail(email)
        val oldPass = user!!.password

        //when
        mockMvc.perform(post("/reset-password")
                .header("email", email)

        ).andExpect(MockMvcResultMatchers.status().isOk)
        val tokenId = forgottenPasswordTokenRepository.getTokenByEmail(email)!!.id

        //and
        val newPassString = "Secret-Password123!"
        mockMvc.perform(post("/new-password").header("tokenId", tokenId).header("newPassword", newPassString))
                .andExpect(MockMvcResultMatchers.status().isOk)
        val newPass = userRepository.getVeloUserByEmail(email)!!.password

        //then
        oldPass `should not be equal to` newPass
        mockMvc.perform(post(securityProperties.loginUrl).header("Email", email).header("Password", "Password123!"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
        mockMvc.perform(post(securityProperties.loginUrl).header("Email", email).header("Password", newPassString))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

}