package pl.edu.pw.mini.velobackend.infrastructure.strava.dao

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.StravaUser
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
import pl.edu.pw.mini.velobackend.infrastructure.strava.dto.StravaUserDto
import pl.edu.pw.mini.velobackend.infrastructure.strava.dto.toStravaUserDto
import java.util.UUID

@Repository
class StravaUserMongoRepository(
        val mongoTemplate: MongoTemplate
) : StravaUserRepository {
    override fun getStravaUserById(id: Int): StravaUser? = findStravaUserByKey("id", id)

    override fun getStravaUserByAthleteId(id: UUID): StravaUser? = findStravaUserByKey("athleteId", id)

    override fun addStravaUser(stravaUser: StravaUser) {
        mongoTemplate.save(stravaUser.toStravaUserDto())
    }

    override fun updateTokenPair(old: TokenPair, new: TokenPair) {
        val user = mongoTemplate.findOne(Query.query(
                Criteria.where("tokenPair").`is`(old)), StravaUserDto::class.java
        )
        if (user != null) {
            mongoTemplate.save(StravaUserDto(
                    id = user.id,
                    tokenPair = new,
                    athleteId = user.athleteId
            ))
        }
    }

    private fun findStravaUserByKey(keyName: String, value: Any): StravaUser? {
        val dto = mongoTemplate.findOne(Query.query(
                Criteria.where(keyName).`is`(value)), StravaUserDto::class.java
        )
        return dto?.toStravaUser()
    }

}