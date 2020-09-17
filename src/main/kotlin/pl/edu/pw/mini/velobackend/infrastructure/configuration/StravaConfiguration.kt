package pl.edu.pw.mini.velobackend.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(StravaProperties::class)
class StravaConfiguration(val stravaProperties: StravaProperties)


@ConstructorBinding
@ConfigurationProperties(prefix = "strava")
data class StravaProperties(
        val tokenUrl: String,
        val apiUrl: String,
        val clientSecret: String,
        val clientId: String
)