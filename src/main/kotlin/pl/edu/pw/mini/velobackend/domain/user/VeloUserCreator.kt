package pl.edu.pw.mini.velobackend.domain.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.edu.pw.mini.velobackend.domain.athlete.Athlete
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteRepository
import java.util.UUID

@Service
class VeloUserCreator(
        private val passwordEncoder: PasswordEncoder,
        private val athleteRepository: AthleteRepository,
        private val veloUserRepository: VeloUserRepository
){
     fun createUser(email: String, password: String, firstName: String, lastName: String) {
        val athleteId = UUID.randomUUID()
        val user = VeloUser(
                email = email,
                password = passwordEncoder.encode(password),
                firstName = firstName,
                lastName = lastName,
                athleteId = athleteId
        )
        veloUserRepository.saveVeloUser(user)
        athleteRepository.addAthlete(Athlete.Builder(email).id(athleteId).firstName(user.firstName).lastName(user.lastName).build())
    }
}