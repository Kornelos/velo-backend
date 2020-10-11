package pl.edu.pw.mini.velobackend.infrastructure.security

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import java.time.Instant

data class JwtTokenDto(
        @Id val token: String,
        @Indexed(name = "expire", expireAfterSeconds = 0, background = true)
        val expire: Instant
)