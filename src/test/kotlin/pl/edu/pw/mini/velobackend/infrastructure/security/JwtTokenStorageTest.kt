package pl.edu.pw.mini.velobackend.infrastructure.security

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.edu.pw.mini.velobackend.infrastructure.security.dto.JwtTokenRepository
import java.lang.Thread.sleep
import java.time.Instant

@SpringBootTest
class JwtTokenStorageTest {

    @Autowired
    lateinit var tokenRepository: JwtTokenRepository

    @Test
    fun `should expire after ttl`() {
        //given
        val token = JwtTokenDto("token", Instant.now().plusMillis(10))

        //when
        tokenRepository.saveToken(token)
        sleep(11)

        //then
        tokenRepository.tokenExistsByValue("token") `should be equal to` false
    }
}