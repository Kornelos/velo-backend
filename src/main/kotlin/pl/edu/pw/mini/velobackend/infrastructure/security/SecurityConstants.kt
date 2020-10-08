package pl.edu.pw.mini.velobackend.infrastructure.security

object SecurityConstants {

    // JWT token defaults
    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "
    const val TOKEN_TYPE = "JWT"
    const val TOKEN_ISSUER = "velo-backend"
    const val TOKEN_AUDIENCE = "velo-frontend"

    val PASSWORD_REGEX = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])(?=.{8,})")
    val EMAIL_REGEX = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
}