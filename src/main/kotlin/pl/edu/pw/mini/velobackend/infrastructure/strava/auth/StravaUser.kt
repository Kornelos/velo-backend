package pl.edu.pw.mini.velobackend.infrastructure.strava.auth

import java.util.UUID

data class StravaUser(
        val id: Int,
        val tokenPair: TokenPair,
        val athleteId: UUID?
)