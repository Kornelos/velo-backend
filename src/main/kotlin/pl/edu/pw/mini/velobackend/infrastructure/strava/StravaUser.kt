package pl.edu.pw.mini.velobackend.infrastructure.strava

import java.time.LocalDateTime
import java.util.UUID

data class StravaUser(
        val id: Int,
        val accessToken: String?,
        val refreshToken: String?,
        val expirationDate: LocalDateTime?,
        val athleteId: UUID?
)