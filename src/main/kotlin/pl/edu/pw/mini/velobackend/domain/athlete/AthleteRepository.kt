package pl.edu.pw.mini.velobackend.domain.athlete

import java.util.UUID

interface AthleteRepository {
    fun getAthleteById(id: UUID): Athlete?
    fun getAthletesByIds(ids: Collection<UUID>): Collection<Athlete>
    fun addAthlete(athlete: Athlete)
    fun getAthleteByEmail(email: String): Athlete?
    fun changeStravaConnectedForAthleteWithEmail(email: String, isStravaConnected: Boolean)
}