package pl.edu.pw.mini.velobackend.infrastructure.strava.dao

import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.StravaUser
import pl.edu.pw.mini.velobackend.infrastructure.strava.auth.TokenPair
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

    override fun updateTokenPair(old: TokenPair, new: TokenPair) {
        val user = stravaUsers.find { it.tokenPair == old }
        if (user != null) {
            stravaUsers.remove(user)
            stravaUsers.add(StravaUser(user.id, new, user.athleteId))
        }
    }

}