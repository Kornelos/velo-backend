package pl.edu.pw.mini.velobackend.domain.user

interface VeloUserRepository {
    fun addVeloUser(user: VeloUser)
    fun findVeloUserByEmail(email: String): VeloUser?
    fun deleteVeloUser(user: VeloUser)
    fun changePasswordForVeloUserWithEmail(email: String, password: String)
}