package pl.edu.pw.mini.velobackend.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "security")
data class SecurityProperties(
        val loginUrl: String,
        val jwtSecret: String
)