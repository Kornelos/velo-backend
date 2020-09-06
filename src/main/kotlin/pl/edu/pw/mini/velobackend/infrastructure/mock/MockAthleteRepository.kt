package pl.edu.pw.mini.velobackend.infrastructure.mock

import org.springframework.stereotype.Component
import pl.edu.pw.mini.velobackend.domain.athlete.Athlete
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteRepository
import java.util.ArrayList
import java.util.UUID

@Component
class MockAthleteRepository : AthleteRepository {

    private val athletes = ArrayList<Athlete>()

    override fun getAthleteById(id: UUID): Athlete? = athletes.find { it.id == id }

    override fun getAthletesByIds(ids: Collection<UUID>): Collection<Athlete> = athletes.filter { ids.contains(it.id) }

    override fun addAthlete(athlete: Athlete) {
        if (athletes.none { it.id == athlete.id }) {
            athletes.add(athlete)
        }
    }

}