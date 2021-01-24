package pl.edu.pw.mini.velobackend.api.authentication

import org.amshove.kluent.`should be equal to`
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
        registerUser("email@test.com")
        val user = userRepository.getVeloUserByEmail("email@test.com")
        val oldPass = user!!.password

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/reset-password")
                .header("email", "email@test.com")

        ).andExpect(MockMvcResultMatchers.status().isOk)
        val tokenId = forgottenPasswordTokenRepository.getTokenByEmail("email@test.com")!!.id

        //and
        mockMvc.perform(MockMvcRequestBuilders.post("/new-password").header("tokenId",tokenId).header("newPassword","Secret-Password123!"))
                .andExpect(MockMvcResultMatchers.status().isOk)
        val newPass = userRepository.getVeloUserByEmail("email@test.com")!!.password

        //then
        oldPass `should not be equal to` newPass
        newPass `should be equal to` "Secret-Password123!"
    }

}