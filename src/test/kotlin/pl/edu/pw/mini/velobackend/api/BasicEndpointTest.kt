package pl.edu.pw.mini.velobackend.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import pl.edu.pw.mini.velobackend.wiremock.WiremockConfig

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 27042)
@ActiveProfiles("test")
@Import(WiremockConfig::class)
abstract class BasicEndpointTest {
    @Autowired
    lateinit var mockMvc: MockMvc
}