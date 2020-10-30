package pl.edu.pw.mini.velobackend.domain.user

import java.util.UUID

interface VeloUserRepository {
    fun saveVeloUser(user: VeloUser)
    fun getVeloUserByEmail(email: String): VeloUser?
    fun deleteVeloUser(user: VeloUser)
    fun changePasswordForVeloUserWithEmail(email: String, password: String)
    fun changeStravaConnectedForVeloUserWithEmail(email: String, isStravaConnected: Boolean)
    fun addAthleteForVeloUserWithEmail(email: String, athleteUUID: UUID)
}