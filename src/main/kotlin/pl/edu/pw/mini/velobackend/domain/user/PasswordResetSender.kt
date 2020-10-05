package pl.edu.pw.mini.velobackend.domain.user

interface PasswordResetSender {
    fun sendPasswordResetMessage(email: String, firstname: String)
}