package pl.edu.pw.mini.velobackend.infrastructure.user

import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordToken
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordTokenRepository
import java.util.UUID

@Repository
class MockForgottenPasswordTokenRepository : ForgottenPasswordTokenRepository {

    val tokens = ArrayList<ForgottenPasswordToken>()
    override fun addToken(forgottenPasswordToken: ForgottenPasswordToken) {
        tokens.removeIf { it.email == forgottenPasswordToken.email }
        tokens.add(forgottenPasswordToken)
    }

    override fun deleteToken(forgottenPasswordToken: ForgottenPasswordToken) {
        tokens.remove(forgottenPasswordToken)
    }

    override fun getTokenById(id: UUID): ForgottenPasswordToken? = tokens.find { it.id == id }

    override fun getTokenByEmail(email: String): ForgottenPasswordToken? = tokens.find { it.email == email }


}