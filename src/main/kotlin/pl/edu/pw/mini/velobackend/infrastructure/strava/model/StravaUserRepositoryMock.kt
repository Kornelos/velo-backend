package pl.edu.pw.mini.velobackend.infrastructure.strava.model

import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.infrastructure.strava.StravaUser
import java.util.UUID

@Repository
class StravaUserRepositoryMock : StravaUserRepository {

    private val stravaUsers = ArrayList<StravaUser>()

    override fun getStravaUserById(id: Int): StravaUser? = stravaUsers.find { it.id == id }

    override fun getStravaUserByAthleteId(id: UUID): StravaUser? = stravaUsers.find { it.athleteId == id }

    override fun addStravaUser(stravaUser: StravaUser) {
        if (getStravaUserById(stravaUser.id) == null) {
            stravaUsers.add(stravaUser)
        }
    }

}