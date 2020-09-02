package pl.edu.pw.mini.velobackend.domain.athlete

import java.util.*

interface AthleteRepository {
    fun getAthleteById(id: UUID): Athlete
    fun getAthletesByIds(ids: Collection<UUID>): Collection<Athlete>
    fun addAthlete(athlete: Athlete)
}