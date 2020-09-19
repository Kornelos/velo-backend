package pl.edu.pw.mini.velobackend.infrastructure.strava.auth

import java.time.LocalDateTime

data class TokenPair(
        val accessToken: AccessToken,
        val refreshToken: RefreshToken,
        val expirationDate: LocalDateTime
)

typealias RefreshToken = String
typealias AccessToken = String