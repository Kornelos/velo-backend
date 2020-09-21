package pl.edu.pw.mini.velobackend.wiremock

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.contract.wiremock.WireMockConfigurationCustomizer
import org.springframework.context.annotation.Bean
import pl.edu.pw.mini.velobackend.api.strava.utils.StreamTransformer


@TestConfiguration
class WiremockConfig {
    @Bean
    fun optionsCustomizer(): WireMockConfigurationCustomizer? {
        return WireMockConfigurationCustomizer { options ->
            options.extensions(StreamTransformer()).port(27042)
        }
    }


}