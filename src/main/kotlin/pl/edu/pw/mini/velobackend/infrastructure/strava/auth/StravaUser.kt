package pl.edu.pw.mini.velobackend.infrastructure.strava.auth

import java.util.UUID

data class StravaUser(
        val id: Int,
//        val accessToken: String,
//        val refreshToken: String,
//        val expirationDate: LocalDateTime?,
        val tokenPair: TokenPair,
        val athleteId: UUID?
)