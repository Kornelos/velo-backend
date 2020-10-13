package pl.edu.pw.mini.velobackend.infrastructure.user.dal

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordToken
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordTokenRepository
import pl.edu.pw.mini.velobackend.infrastructure.user.dto.ForgottenPasswordTokenDto
import pl.edu.pw.mini.velobackend.infrastructure.user.dto.toForgottenPasswordTokenDto
import java.util.UUID

@Repository
class ForgottenPasswordTokenMongoRepository(
        val mongoTemplate: MongoTemplate
) : ForgottenPasswordTokenRepository {
    override fun addToken(forgottenPasswordToken: ForgottenPasswordToken) {
        mongoTemplate.insert(forgottenPasswordToken.toForgottenPasswordTokenDto())
    }

    override fun deleteToken(forgottenPasswordToken: ForgottenPasswordToken) {
        mongoTemplate.remove(forgottenPasswordToken.toForgottenPasswordTokenDto())
    }

    override fun getTokenById(id: UUID): ForgottenPasswordToken? = findTokenByKey("id", id)

    override fun getTokenByEmail(email: String): ForgottenPasswordToken? = findTokenByKey("email", email)


    private fun findTokenByKey(keyName: String, value: Any): ForgottenPasswordToken? {
        val tokenDto: ForgottenPasswordTokenDto? = mongoTemplate.findOne(Query.query(
                Criteria.where(keyName).`is`(value)), ForgottenPasswordTokenDto::class.java
        )
        return tokenDto?.toForgottenPasswordToken()
    }

}