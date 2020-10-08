package pl.edu.pw.mini.velobackend.api

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.edu.pw.mini.velobackend.infrastructure.configuration.FrontendProperties


class MockWorkoutEndpointTest(@Autowired val frontendProperties: FrontendProperties) : BasicEndpointTest() {

    @Test
    fun `should allow cross origin request from defined origin`() {
        mockMvc.perform(get("/mock-workout")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", frontendProperties.origin))
                .andExpect(status().isOk)
    }

    @Disabled("Not important feature yet")
    @Test
    fun `should not allow cross origin request from not allowed origin`() {
        mockMvc.perform(get("/mock-workout")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://notallowed.bad"))
                .andExpect(status().isForbidden)
    }
}