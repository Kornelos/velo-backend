package pl.edu.pw.mini.velobackend.api.authentication

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.edu.pw.mini.velobackend.api.BasicEndpointTest
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.user.dto.VeloUserDto

class RegisterEndpointTest : BasicEndpointTest() {

    @Autowired
    lateinit var veloUserRepository: VeloUserRepository

    @AfterEach
    fun cleanMongo() {
        mongoTemplate.dropCollection(VeloUserDto::class.java)
    }

    @Test
    fun `should register user if email does not exists in db`() {
        //when
        mockMvc.perform(post("/register")
                .header("Username", "email@test.com")
                .header("Password", "pass")
                .header("firstName", "first")
                .header("lastName", "last")
        ).andExpect(status().isCreated)
        val user = veloUserRepository.findVeloUserByEmail("email@test.com")!!

        //then
        user `should not be` null
        user.email `should be equal to` "email@test.com"
        user.password `should not be equal to` "pass" // should be encoded
        user.firstName `should be equal to` "first"
        user.lastName `should be equal to` "last"

    }

    @Test
    fun `should not register user if email does not exists in db`() {
        //given
        mockMvc.perform(post("/register")
                .header("Username", "email@test.com")
                .header("Password", "pass")
                .header("firstName", "test")
                .header("lastName", "coach")
        ).andReturn()
        //when registering same email again
        mockMvc.perform(post("/register")
                .header("Username", "email@test.com")
                .header("Password", "pass")
                .header("firstName", "test")
                .header("lastName", "coach")
        ).andExpect(status().isConflict)

    }
}