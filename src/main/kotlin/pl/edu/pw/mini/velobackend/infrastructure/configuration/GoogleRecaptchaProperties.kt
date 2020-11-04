package pl.edu.pw.mini.velobackend.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix="google.recaptcha")
data class GoogleRecaptchaProperties(
        val key: String,
        val url: String
)
