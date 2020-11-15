package pl.edu.pw.mini.velobackend.infrastructure.user.dal

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import java.util.UUID

@Repository
class VeloUserMongoRepository(
        val mongoTemplate: MongoTemplate
) : VeloUserRepository {
    override fun saveVeloUser(user: VeloUser) {
        mongoTemplate.save(user)
    }

    override fun getVeloUserByEmail(email: String): VeloUser? {
        return findUserByEmail(email)
    }

    override fun deleteVeloUser(user: VeloUser) {
        mongoTemplate.remove(user)
    }

    override fun changePasswordForVeloUserWithEmail(email: String, password: String) {
        val veloUser = findUserByEmail(email)
        if (veloUser != null) {
            mongoTemplate.save(VeloUser(
                    veloUser.id,
                    veloUser.email,
                    password,
                    veloUser.firstName,
                    veloUser.lastName,
                    veloUser.profileImg,
                    veloUser.athleteUUIDs,
                    veloUser.isStravaConnected,
                    veloUser.athleteId
            ))
        }
    }

    override fun changeStravaConnectedForVeloUserWithEmail(email: String, isStravaConnected: Boolean) {
        val veloUser = findUserByEmail(email)
        if (veloUser != null) {
            mongoTemplate.save(VeloUser(
                    veloUser.id,
                    veloUser.email,
                    veloUser.password,
                    veloUser.firstName,
                    veloUser.lastName,
                    veloUser.profileImg,
                    veloUser.athleteUUIDs,
                    isStravaConnected,
                    veloUser.athleteId
            ))
        }
    }

    override fun addAthleteForVeloUserWithEmail(email: String, athleteUUID: UUID) {
        val veloUser: VeloUser? = findUserByEmail(email)
        if (veloUser != null) {
            veloUser.athleteUUIDs.add(athleteUUID)
            mongoTemplate.save(veloUser)
        }
    }

    private fun findUserByEmail(email: String): VeloUser? = mongoTemplate.findOne(Query.query(
            Criteria.where("email").`is`(email)), VeloUser::class.java)
}