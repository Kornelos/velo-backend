package pl.edu.pw.mini.velobackend.infrastructure.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pl.edu.pw.mini.velobackend.domain.user.VeloUserRepository


@Service
class SecUserDetailService(
        val userRepository: VeloUserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        check(username != null)
        val veloUser = userRepository.findVeloUserByEmail(username)
        return if (veloUser == null) {
            throw UsernameNotFoundException(username)
        } else {
            SecUserDetails(veloUser)
        }
    }
}