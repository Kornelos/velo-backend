package pl.edu.pw.mini.velobackend.infrastructure.strava

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.edu.pw.mini.velobackend.domain.athlete.Athlete
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteRepository
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.StravaUser
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
import pl.edu.pw.mini.velobackend.infrastructure.strava.dao.StravaUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.ActivityType
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.SummaryActivity
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams.StreamSet
import pl.edu.pw.mini.velobackend.infrastructure.utils.logger
import pl.edu.pw.mini.velobackend.infrastructure.workout.WorkoutMapper
import java.time.Instant
import java.util.UUID

@Service
class StravaService(
        val stravaClient: StravaClient,
        val stravaUserRepository: StravaUserRepository,
        val athleteRepository: AthleteRepository,
        val workoutRepository: WorkoutRepository,
        val veloUserRepository: VeloUserRepository
) {
    val logger by logger()

    fun createStravaUser(code: String, scope: String, state: String): StravaUser {
        val authorizationResponse = stravaClient.exchangeCodeForAccessToken(code)

        val email = getEmailFromState(state)
        val athlete: Athlete = athleteRepository.getAthleteByEmail(email)
                ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Athlete for profile with $email does not exist")

        val tokenPair = TokenPair(
                accessToken = authorizationResponse.accessToken,
                refreshToken = authorizationResponse.refreshToken,
                expirationDate = authorizationResponse.expiresAt
        )
        val stravaUser = StravaUser(
                authorizationResponse.athlete.id,
                tokenPair,
                athlete.id
        )

        stravaUserRepository.addStravaUser(stravaUser)
        veloUserRepository.changeStravaConnectedForVeloUserWithEmail(email, true)
        athleteRepository.changeStravaConnectedForAthleteWithEmail(email, true)
        veloUserRepository.addAthleteForVeloUserWithEmail(email, athlete.id) // add self as trained athlete

        logger.info("Successfully added Strava user {} for athlete with email {}", stravaUser.id, email)

        return stravaUser
    }

    fun updateWorkoutsFor(athleteId: UUID, before: Instant, after: Instant): List<Workout> {
        val stravaUser = stravaUserRepository.getStravaUserByAthleteId(athleteId)
        return if (stravaUser != null) {
            val activitiesSummary: List<SummaryActivity> = stravaClient.getWorkoutsForStravaUser(stravaUser.tokenPair, before, after)
                    .filter { it.type == ActivityType.Ride || it.type == ActivityType.VirtualRide } // removing non-bike activities
            val stravaWorkouts = getStreamsForNewWorkouts(activitiesSummary, stravaUser)
            stravaWorkouts.map { (summary, streams) -> saveWorkout(streams, summary, athleteId) }
        } else emptyList()
    }

    private fun getStreamsForNewWorkouts(activitiesSummary: List<SummaryActivity>, stravaUser: StravaUser): List<Pair<SummaryActivity, StreamSet?>> {
        return activitiesSummary.map {
            var streamSet: StreamSet? = null
            if (!workoutRepository.workoutExists(it.id ?: 0)) {
                streamSet = stravaClient.getWorkoutStreams(stravaUser.tokenPair, it)
            }
            it to streamSet
        }.filter { it.second != null }
    }

    private fun saveWorkout(streams: StreamSet?, summary: SummaryActivity, athleteId: UUID): Workout {
        check(streams != null)
        val workout = WorkoutMapper.createWorkout(summary, streams, athleteId)
        workoutRepository.addWorkout(workout)
        return workout
    }

    private fun getEmailFromState(state: String) = state.split(":")[1].trim()


}

