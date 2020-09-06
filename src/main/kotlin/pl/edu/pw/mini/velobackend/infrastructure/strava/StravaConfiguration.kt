package pl.edu.pw.mini.velobackend.infrastructure.strava

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "strava")
data class StravaConfiguration(
        val tokenUrl: String,
        val apiUrl: String,
        val clientSecret: String,
        val clientId: String
)