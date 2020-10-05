package pl.edu.pw.mini.velobackend.api.authentication

import org.amshove.kluent.`should not be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordTokenRepository
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository

class PasswordResetTest : BasicEndpointTest() {

    @Autowired
    lateinit var forgottenPasswordTokenRepository: ForgottenPasswordTokenRepository

    @Autowired
    lateinit var userRepository: VeloUserRepository

    @Test
    fun `should reset user password`() {
        //given user is registered
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .header("Username", "email@test.com")
                .header("Password", "pass")
                .header("firstName", "first")
                .header("lastName", "last")
        ).andExpect(MockMvcResultMatchers.status().isCreated)
        val user = userRepository.findVeloUserByEmail("email@test.com")

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/reset-password")
                .header("email", "email@test.com")

        ).andExpect(MockMvcResultMatchers.status().isOk)
        val tokenId = forgottenPasswordTokenRepository.getTokenByEmail("email@test.com")!!.id

        //and
        mockMvc.perform(MockMvcRequestBuilders.get("/confirm-password?tokenId=$tokenId"))
                .andExpect(MockMvcResultMatchers.status().isOk)
        val userNewPass = userRepository.findVeloUserByEmail("email@test.com")

        //then
        user!!.password `should not be equal to` userNewPass!!.password
    }

}