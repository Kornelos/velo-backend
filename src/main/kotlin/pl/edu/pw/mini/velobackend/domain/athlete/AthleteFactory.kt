package pl.edu.pw.mini.velobackend.domain.athlete

import pl.edu.pw.mini.velobackend.infrastructure.strava.model.StravaAthlete

class AthleteFactory {

    fun fromStravaAthlete(stravaAthlete: StravaAthlete): Athlete {
        return Athlete.Builder()
                .firstName(stravaAthlete.firstName)
                .lastName(stravaAthlete.lastName)
                .gender(stravaAthlete.sex)
                .build()
    }
}