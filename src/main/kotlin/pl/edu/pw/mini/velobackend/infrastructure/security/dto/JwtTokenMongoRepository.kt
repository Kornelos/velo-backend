package pl.edu.pw.mini.velobackend.infrastructure.security.dto

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.infrastructure.security.JwtTokenDto
import java.time.Instant

@Repository
class JwtTokenMongoRepository(
        val mongoTemplate: MongoTemplate
) : JwtTokenRepository {
    override fun saveToken(token: JwtTokenDto) {
        mongoTemplate.save(token)
    }

    override fun deleteToken(token: JwtTokenDto) {
        mongoTemplate.remove(token)
    }

    override fun deleteTokenByValue(tokenValue: String) {
        mongoTemplate.remove(Query.query(Criteria.where("token").`is`(tokenValue)), JwtTokenDto::class.java)
    }

    override fun tokenExistsByValue(tokenValue: String): Boolean {
        val maybeToken = mongoTemplate.findOne(Query.query(Criteria.where("token").`is`(tokenValue)), JwtTokenDto::class.java)

        return if (maybeToken != null && maybeToken.expire.isAfter(Instant.now())) {
            true
        } else if (maybeToken != null) {
            deleteToken(maybeToken)
            false
        } else false
    }
}