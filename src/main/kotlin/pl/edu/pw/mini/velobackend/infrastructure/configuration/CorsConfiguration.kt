package pl.edu.pw.mini.velobackend.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableConfigurationProperties(FrontendProperties::class)
class CorsConfiguration(val frontendProperties: FrontendProperties) {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer =
            object : WebMvcConfigurer {
                override fun addCorsMappings(registry: CorsRegistry) {
                    registry.addMapping("/**").allowedOrigins(frontendProperties.origin)
                }
            }

}

@ConstructorBinding
@ConfigurationProperties(prefix = "frontend")
data class FrontendProperties(val origin: String)
