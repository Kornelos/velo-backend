package pl.edu.pw.mini.velobackend.infrastructure.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pl.edu.pw.mini.velobackend.domain.user.VeloUser

data class SecUserDetails(
        val user: VeloUser
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(GrantedAuthority { "ROLE_USER" })

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.email

    // todo implement those if we want them in user
    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}