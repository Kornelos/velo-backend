package pl.edu.pw.mini.velobackend.domain.athlete

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.utils.logger
import java.util.UUID

@Service
class AthleteService(
        private val athleteRepository: AthleteRepository,
        private val userRepository: VeloUserRepository
) {
    val log by logger()

    fun addAthleteForUserWithEmail(coachEmail: String, athleteEmail: String) {
        val coach: VeloUser = userRepository.getVeloUserByEmail(coachEmail)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Coach with such email does not exist")
        val athlete: Athlete = athleteRepository.getAthleteByEmail(athleteEmail)
                ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)

        coach.athleteUUIDs.add(athlete.id)
        userRepository.saveVeloUser(coach)
    }

    fun getAthleteByEmail(email: String) = athleteRepository.getAthleteByEmail(email)
    fun getAthleteById(athleteId: UUID): Athlete {
        return athleteRepository.getAthleteById(athleteId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Athlete with such ID does not exist")
    }

    fun getAthletesByIds(athleteIds: Collection<UUID>) = athleteRepository.getAthletesByIds(athleteIds)


}