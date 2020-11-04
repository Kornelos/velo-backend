package pl.edu.pw.mini.velobackend.infrastructure.user.dal

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.user.dto.VeloUserDto
import pl.edu.pw.mini.velobackend.infrastructure.user.dto.toVeloUserDto
import java.util.UUID

@Repository
class VeloUserMongoRepository(
        val mongoTemplate: MongoTemplate
) : VeloUserRepository {
    override fun saveVeloUser(user: VeloUser) {
        mongoTemplate.save(user.toVeloUserDto())
    }

    override fun getVeloUserByEmail(email: String): VeloUser? {
        val veloUserDto: VeloUserDto? = findUserDtoByEmail(email)
        return veloUserDto?.toVeloUser()
    }

    override fun deleteVeloUser(user: VeloUser) {
        mongoTemplate.remove(user.toVeloUserDto())
    }

    override fun changePasswordForVeloUserWithEmail(email: String, password: String) {
        val veloUserDto = findUserDtoByEmail(email)
        if (veloUserDto != null) {
            mongoTemplate.save(VeloUserDto(
                    veloUserDto.id,
                    veloUserDto.email,
                    password,
                    veloUserDto.firstName,
                    veloUserDto.lastName,
                    veloUserDto.profileImg,
                    veloUserDto.athleteUUIDs,
                    veloUserDto.isStravaConnected
            ))
        }
    }

    override fun changeStravaConnectedForVeloUserWithEmail(email: String, isStravaConnected: Boolean) {
        val veloUserDto = findUserDtoByEmail(email)
        if (veloUserDto != null) {
            mongoTemplate.save(VeloUserDto(
                    veloUserDto.id,
                    veloUserDto.email,
                    veloUserDto.password,
                    veloUserDto.firstName,
                    veloUserDto.lastName,
                    veloUserDto.profileImg,
                    veloUserDto.athleteUUIDs,
                    isStravaConnected
            ))
        }
    }

    override fun addAthleteForVeloUserWithEmail(email: String, athleteUUID: UUID) {
        val veloUserDto: VeloUserDto? = findUserDtoByEmail(email)
        if (veloUserDto != null) {
            veloUserDto.athleteUUIDs.add(athleteUUID)
            mongoTemplate.save(veloUserDto)
        }
    }

    private fun findUserDtoByEmail(email: String): VeloUserDto? = mongoTemplate.findOne(Query.query(
            Criteria.where("email").`is`(email)), VeloUserDto::class.java)
}