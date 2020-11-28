package pl.edu.pw.mini.velobackend.infrastructure.strava.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.edu.pw.mini.velobackend.infrastructure.serialization.DurationSerializer
import pl.edu.pw.mini.velobackend.infrastructure.serialization.LocalDateTimeEpochSerializer
import java.time.Duration
import java.time.LocalDateTime


@Serializable
data class AuthorizationResponse(
        @SerialName("token_type")
        val tokenType: String,
        @Serializable(with = LocalDateTimeEpochSerializer::class) @SerialName("expires_at")
        val expiresAt: LocalDateTime,
        @Serializable(with = DurationSerializer::class) @SerialName("expires_in")
        val expiresIn: Duration,
        @SerialName("refresh_token")
        val refreshToken: String,
        @SerialName("access_token")
        val accessToken: String,
        val athlete: StravaAthlete? = null
)



