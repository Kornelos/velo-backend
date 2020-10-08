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
}