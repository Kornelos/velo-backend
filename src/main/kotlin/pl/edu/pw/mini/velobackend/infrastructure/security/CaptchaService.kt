package pl.edu.pw.mini.velobackend.infrastructure.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.edu.pw.mini.velobackend.infrastructure.configuration.GoogleRecaptchaProperties

@Service
@EnableConfigurationProperties(GoogleRecaptchaProperties::class)
class CaptchaService(
        val properties: GoogleRecaptchaProperties,
        val restTemplate: RestTemplate
) {


    fun validateNotRobot(captchaResult: String): Boolean{
        if(!properties.enabled){
            return true
        }

        val uriBuilder = UriComponentsBuilder.fromHttpUrl(properties.url)
                .queryParam("secret", properties.key)
                .queryParam("response", captchaResult)

        val response = restTemplate.postForEntity(uriBuilder.toUriString(), null, Map::class.java)


        return response.body?.get("success") as Boolean
    }
}