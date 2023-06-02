package club.hosppy.account.service

import club.hosppy.account.repo.AccountSecretRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val accountSecretRepository: AccountSecretRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val accountSecret = accountSecretRepository.findByEmail(username) ?: throw UsernameNotFoundException("User not found")
        return User(accountSecret.email, accountSecret.passwordHash, listOf())
    }
}