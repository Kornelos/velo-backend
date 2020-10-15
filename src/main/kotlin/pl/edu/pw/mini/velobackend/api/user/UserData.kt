package pl.edu.pw.mini.velobackend.api.user

import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import java.net.URI
import java.util.UUID

data class UserData(
        val email: String,
        val firstName: String,
        val lastName: String,
        var profileImg: URI?,
        val athleteUUIDs: MutableSet<UUID>,
        val isStravaConnected: Boolean
)

fun VeloUser.toUserData() = UserData(email, firstName, lastName, profileImg, athleteUUIDs, isStravaConnected)