package pl.edu.pw.mini.velobackend.infrastructure.strava.model

import pl.edu.pw.mini.velobackend.infrastructure.strava.StravaUser
import java.util.UUID


interface StravaUserRepository {
    fun getStravaUserById(id: Int): StravaUser?
    fun getStravaUserByAthleteId(id: UUID): StravaUser?
    fun addStravaUser(stravaUser: StravaUser)
}