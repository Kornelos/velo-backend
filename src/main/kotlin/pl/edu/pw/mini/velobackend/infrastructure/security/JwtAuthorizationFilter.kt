package pl.edu.pw.mini.velobackend.infrastructure.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import pl.edu.pw.mini.velobackend.infrastructure.configuration.SecurityProperties
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
        authenticationManager: AuthenticationManager,
        val securityProperties: SecurityProperties
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authentication = getAuthentication(request)

        if (authentication != null) {
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(SecurityConstants.TOKEN_HEADER) ?: ""

        if (token.isNotEmpty() && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                val signingKey = securityProperties.jwtSecret.toByteArray()
                val parsedToken = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token.replace("Bearer ", ""))
                val username = parsedToken.body.subject
                val authorities = (parsedToken.body["rol"] as List<*>)
                        .map { authority: Any? -> SimpleGrantedAuthority(authority as String?) }
                if (username.isNotEmpty()) {
                    return UsernamePasswordAuthenticationToken(username, null, authorities)
                }
            } catch (exception: ExpiredJwtException) {
                // log!!.warn("Request to parse expired JWT : {} failed : {}", token, exception.message)
            } catch (exception: UnsupportedJwtException) {
                // log!!.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.message)
            } catch (exception: MalformedJwtException) {
                // log!!.warn("Request to parse invalid JWT : {} failed : {}", token, exception.message)
            } catch (exception: IllegalArgumentException) {
                //log!!.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.message)
            }
        }
        return null
    }
}