package pl.edu.pw.mini.velobackend.domain.user

import java.net.URI
import java.util.UUID

data class VeloUser(
        val id: UUID = UUID.randomUUID(),
        val email: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        var profileImg: URI? = null,
        val athleteUUIDs: MutableSet<UUID> = mutableSetOf(),
        val isStravaConnected: Boolean = false
)