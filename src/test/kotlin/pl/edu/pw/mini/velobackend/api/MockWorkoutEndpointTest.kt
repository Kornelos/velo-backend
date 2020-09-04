package pl.edu.pw.mini.velobackend.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.edu.pw.mini.velobackend.infrastructure.configuration.FrontendProperties


@SpringBootTest
@AutoConfigureMockMvc
class MockWorkoutEndpointTest(@Autowired val mockMvc: MockMvc,
                              @Autowired val frontendProperties: FrontendProperties) {

    @Test
    fun `should allow cross origin request from defined origin`() {
        mockMvc.perform(get("/mock-workout")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", frontendProperties.origin))
                .andExpect(status().isOk)
    }

    @Test
    fun `should not allow cross origin request from not allowed origin`() {
        mockMvc.perform(get("/mock-workout")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://notallowed.bad"))
                .andExpect(status().isForbidden)
    }
}