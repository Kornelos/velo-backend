package pl.edu.pw.mini.velobackend.infrastructure.security.dto

import pl.edu.pw.mini.velobackend.infrastructure.security.JwtTokenDto

interface JwtTokenRepository {
    fun saveToken(token: JwtTokenDto)
    fun deleteToken(token: JwtTokenDto)
    fun deleteTokenByValue(tokenValue: String)
    fun tokenExistsByValue(tokenValue: String): Boolean
}