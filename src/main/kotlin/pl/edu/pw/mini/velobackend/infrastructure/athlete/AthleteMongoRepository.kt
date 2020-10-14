package pl.edu.pw.mini.velobackend.infrastructure.athlete

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.athlete.Athlete
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteRepository
import java.util.UUID

@Repository
class AthleteMongoRepository(
        val mongoTemplate: MongoTemplate
) : AthleteRepository {
    override fun getAthleteById(id: UUID): Athlete? = findAthleteByKey("id", id)

    override fun getAthletesByIds(ids: Collection<UUID>): Collection<Athlete> {
        return ids.map { getAthleteById(it) }.requireNoNulls()
    }

    override fun addAthlete(athlete: Athlete) {
        mongoTemplate.save(athlete.toAthleteDto())
    }

    override fun getAthleteByEmail(email: String): Athlete? = findAthleteByKey("email", email)

    private fun findAthleteByKey(keyName: String, value: Any): Athlete? {
        val athleteDto = mongoTemplate.findOne(Query.query(
                Criteria.where(keyName).`is`(value)), AthleteDto::class.java
        )
        return athleteDto?.toAthlete()
    }
}