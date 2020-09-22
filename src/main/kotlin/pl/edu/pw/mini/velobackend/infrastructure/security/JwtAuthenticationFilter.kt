package pl.edu.pw.mini.velobackend.infrastructure.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.time.Duration
import java.time.Instant
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.streams.toList

class JwtAuthenticationFilter(private val manager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    init {
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL)
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username = request.getHeader("username")
        val password = request.getHeader("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return manager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain, authentication: Authentication) {
        val user = authentication.principal as SecUserDetails

        val roles = user.authorities.stream().map { obj: GrantedAuthority -> obj.authority }.toList()

        val signingKey = SecurityConstants.JWT_SECRET.toByteArray()

        val token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.username)
                .setExpiration(Date.from(Instant.now().plus(Duration.ofDays(7))))
                .claim("rol", roles)
                .compact()
        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token)
    }
}