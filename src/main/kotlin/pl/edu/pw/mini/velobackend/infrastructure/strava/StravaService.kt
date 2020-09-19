package pl.edu.pw.mini.velobackend.infrastructure.strava

import org.springframework.stereotype.Service
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteFactory
import pl.edu.pw.mini.velobackend.domain.athlete.AthleteRepository
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.StravaUser
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
import pl.edu.pw.mini.velobackend.infrastructure.strava.dao.StravaUserRepository
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.SummaryActivity
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams.StreamSet
import java.time.Instant
import java.util.UUID

@Service
class StravaService(
        val stravaClient: StravaClient,
        val stravaUserRepository: StravaUserRepository,
        val athleteRepository: AthleteRepository
) {

    fun createStravaUser(code: String, scope: String, state: String): StravaUser {
        val authorizationResponse = stravaClient.exchangeCodeForAccessToken(code)

        val athlete = AthleteFactory().fromStravaAthlete(authorizationResponse.athlete)
        athleteRepository.addAthlete(athlete)

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
        return stravaUser
    }

    fun updateWorkoutsFor(athleteId: UUID, before: Instant, after: Instant): String {
        val stravaUser = stravaUserRepository.getStravaUserByAthleteId(athleteId)
        if (stravaUser != null) {
            val activitiesSummary: List<SummaryActivity> = stravaClient.getWorkoutsForStravaUser(stravaUser.tokenPair, before, after)

            //todo: foreach activity download streams and save to domain-type activity
            val workouts = activitiesSummary.map {
                val streamSet = stravaClient.getWorkoutStreams(stravaUser.tokenPair, it)
                // todo: create domain workout from streams and activity summary
                it to streamSet
            }
            println(workouts)
            return "success"
        }
        return "failed."
    }

    private fun createWorkout(activity: SummaryActivity, streamSet: StreamSet) {
//        Workout(
//
//             type = WorkoutType.RoadBike
//        )
    }


}

