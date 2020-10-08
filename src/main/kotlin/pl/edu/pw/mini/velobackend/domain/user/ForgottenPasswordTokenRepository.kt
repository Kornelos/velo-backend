package pl.edu.pw.mini.velobackend.domain.user

import java.util.UUID

interface ForgottenPasswordTokenRepository {
    fun addToken(forgottenPasswordToken: ForgottenPasswordToken)
    fun deleteToken(forgottenPasswordToken: ForgottenPasswordToken)
    fun getTokenById(id: UUID): ForgottenPasswordToken?
    fun getTokenByEmail(email: String): ForgottenPasswordToken?
}