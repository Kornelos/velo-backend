package pl.edu.pw.mini.velobackend.infrastructure.strava.dto

import org.springframework.data.annotation.Id
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.StravaUser
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
import java.util.UUID

data class StravaUserDto(
        @Id val id: Int,
        val tokenPair: TokenPair,
        val athleteId: UUID?
) {
    fun toStravaUser() = StravaUser(id, tokenPair, athleteId)
}

fun StravaUser.toStravaUserDto() = StravaUserDto(id, tokenPair, athleteId)