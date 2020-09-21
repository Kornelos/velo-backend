package pl.edu.pw.mini.velobackend.infrastructure.strava.dao

import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.StravaUser
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
import java.util.UUID


interface StravaUserRepository {
    fun getStravaUserById(id: Int): StravaUser?
    fun getStravaUserByAthleteId(id: UUID): StravaUser?
    fun addStravaUser(stravaUser: StravaUser)
    fun updateTokenPair(old: TokenPair, new: TokenPair)
}