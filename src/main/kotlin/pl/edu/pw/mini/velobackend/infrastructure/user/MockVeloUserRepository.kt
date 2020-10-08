package pl.edu.pw.mini.velobackend.infrastructure.user

import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.user.VeloUser
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository

@Repository
class MockVeloUserRepository : VeloUserRepository {

    private val veloUsers = ArrayList<VeloUser>()

    override fun addVeloUser(user: VeloUser) {
        veloUsers.add(user)
    }

    override fun findVeloUserByEmail(email: String): VeloUser? = veloUsers.find { it.email == email }

    override fun deleteVeloUser(user: VeloUser) {
        veloUsers.remove(user)
    }

    override fun changePasswordForVeloUserWithEmail(email: String, password: String) {
        val user = findVeloUserByEmail(email)
        if (user != null) {
            veloUsers.remove(user)
            veloUsers.add(userWithNewPassword(user, password))
        }
    }

    private fun userWithNewPassword(veloUser: VeloUser, newPassword: String) = VeloUser(
            id = veloUser.id,
            email = veloUser.email,
            password = newPassword,
            firstName = veloUser.firstName,
            lastName = veloUser.lastName,
            athleteUUIDs = veloUser.athleteUUIDs
    )
}