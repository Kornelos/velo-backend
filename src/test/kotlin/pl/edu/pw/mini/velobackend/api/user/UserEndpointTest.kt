package pl.edu.pw.mini.velobackend.api.user

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository


class UserEndpointTest : BasicEndpointTest() {

    @Autowired
    lateinit var veloUserRepository: VeloUserRepository

    @Test
    fun `should return user by email`() {
        //given
        val email = "test@mail.com"
        val user = VeloUser(email = email, password = "pwd", firstName = "fnm", lastName = "lnm")
        veloUserRepository.addVeloUser(user)

        //when
        val result = mockMvc.perform(get(UriComponentsBuilder.fromUriString("/user").queryParam("email", email).toUriString())).andReturn()

        //then
        result.response.contentAsString `should be equal to` """{"email":"test@mail.com","firstName":"fnm","lastName":"lnm","profileImg":null,"athleteUUIDs":[]}"""

        //cleanup
        veloUserRepository.deleteVeloUser(user)
    }
}