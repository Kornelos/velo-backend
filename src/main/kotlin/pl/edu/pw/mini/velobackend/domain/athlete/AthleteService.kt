package pl.edu.pw.mini.velobackend.domain.athlete

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import pl.edu.pw.mini.velobackend.infrastructure.utils.logger
import pl.edu.pw.mini.velobackend.infrastructure.workout.WorkoutMeta
import pl.edu.pw.mini.velobackend.infrastructure.workout.toWorkoutMeta
import java.util.UUID

@Service
class AthleteService(
        private val athleteRepository: AthleteRepository,
        private val userRepository: VeloUserRepository,
        private val workoutRepository: WorkoutRepository
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

    fun getAthleteById(athleteId: UUID): Athlete {
        return athleteRepository.getAthleteById(athleteId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Athlete with such ID does not exist")
    }

    fun getAthletesByIdsWithWorkoutMetadata(athleteIds: Collection<UUID>): List<Pair<Athlete, List<WorkoutMeta>>> {
        val athletes = athleteRepository.getAthletesByIds(athleteIds)
        return athletes.map { athlete ->
            athlete to workoutRepository.getWorkoutsForAthleteId(athlete.id).map { it.toWorkoutMeta() }
        }

    }

    fun removeAthleteForUserWithEmail(coachEmail: String, athleteEmail: String): AthleteRemovalResult {
        val coach: VeloUser = userRepository.getVeloUserByEmail(coachEmail)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Coach with such email does not exist")
        val athlete: Athlete = athleteRepository.getAthleteByEmail(athleteEmail)
                ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)

        return if(coach.athleteUUIDs.remove(athlete.id)){
            userRepository.saveVeloUser(coach)
            SuccessAthleteRemoval()
        } else{
            log.error("Athlete $athleteEmail removal failed, $coachEmail athlete list does not contain selected athlete.")
            FailedAthleteRemoval()
        }
    }
}

abstract class AthleteRemovalResult(val success: Boolean, val message: String)
class SuccessAthleteRemoval : AthleteRemovalResult(true,"Athlete successfully removed from coach")
class FailedAthleteRemoval : AthleteRemovalResult(false, "Athlete removal from coach failed")