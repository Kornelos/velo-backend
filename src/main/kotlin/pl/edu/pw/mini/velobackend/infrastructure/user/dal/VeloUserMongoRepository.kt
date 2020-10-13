package pl.edu.pw.mini.velobackend.infrastructure.user.dal

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.user.dto.VeloUserDto
import pl.edu.pw.mini.velobackend.infrastructure.user.dto.toVeloUserDto

@Repository
class VeloUserMongoRepository(
        val mongoTemplate: MongoTemplate
) : VeloUserRepository {
    override fun addVeloUser(user: VeloUser) {
        mongoTemplate.insert(user.toVeloUserDto())
    }

    override fun findVeloUserByEmail(email: String): VeloUser? {
        val veloUserDto: VeloUserDto? = mongoTemplate.findOne(Query.query(
                Criteria.where("email").`is`(email)), VeloUserDto::class.java)
        return veloUserDto?.toVeloUser()
    }

    override fun deleteVeloUser(user: VeloUser) {
        mongoTemplate.remove(user.toVeloUserDto())
    }

    override fun changePasswordForVeloUserWithEmail(email: String, password: String) {
        val veloUserDto: VeloUserDto? = mongoTemplate.findOne(Query.query(
                Criteria.where("email").`is`(email)), VeloUserDto::class.java)
        if (veloUserDto != null) {
            mongoTemplate.save(VeloUserDto(
                    veloUserDto.id,
                    veloUserDto.email,
                    password,
                    veloUserDto.firstName,
                    veloUserDto.lastName,
                    veloUserDto.profileImg,
                    veloUserDto.athleteUUIDs
            ))
        }
    }
}