package pl.edu.pw.mini.velobackend.infrastructure.user

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import org.springframework.stereotype.Service
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordToken
import pl.edu.pw.mini.velobackend.domain.user.ForgottenPasswordTokenRepository
import pl.edu.pw.mini.velobackend.domain.user.PasswordResetSender

@Service
class SendgridPasswordResetSender(
        private val forgottenPasswordTokenRepository: ForgottenPasswordTokenRepository,
        private val sendGrid: SendGrid
) : PasswordResetSender {
    //todo: add properties for sender and url
    private val from = "skorkak@student.mini.pw.edu.pl"
    private val subject = "Velo app password reset"
    private val templateId = "d-6bb1df27ddf742b3a512f7290d216adc"

    override fun sendPasswordResetMessage(email: String, firstname: String) {
        val resetToken = ForgottenPasswordToken(email = email)
        forgottenPasswordTokenRepository.addToken(resetToken)

        val request = Request()
        request.method = Method.POST
        request.endpoint = "mail/send"
        request.body = """{"personalizations": [{"to": [{"email": "$email"}],"dynamic_template_data": {"firstname": "$firstname","url" : "http://localhost:8081/confirm-password?tokenId=${resetToken.id}"},"subject": "$subject"}],"from": {"email": "$from","name": "velo"},"reply_to": {"email": "$from","name": "velo"},"template_id": "$templateId"}"""
        sendGrid.api(request)
    }

}