package pl.edu.pw.mini.velobackend.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "frontend")
data class FrontendProperties(val origin: String)
