package pl.edu.pw.mini.velobackend.infrastructure.user.dto

import org.springframework.data.annotation.Id
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import java.net.URI
import java.util.UUID

data class VeloUserDto(
        @Id val id: UUID,
        val email: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        var profileImg: URI? = null,
        val athleteUUIDs: MutableSet<UUID> = mutableSetOf()
) {
    fun toVeloUser() = VeloUser(id, email, password, firstName, lastName, profileImg, athleteUUIDs)
}

fun VeloUser.toVeloUserDto() = VeloUserDto(id, email, password, firstName, lastName, profileImg, athleteUUIDs)