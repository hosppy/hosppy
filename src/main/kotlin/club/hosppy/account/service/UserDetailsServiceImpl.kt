package club.hosppy.account.service

import club.hosppy.account.model.User
import club.hosppy.account.repo.AccountRepository
import club.hosppy.account.repo.AccountSecretRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val accountRepository: AccountRepository,
    private val accountSecretRepository: AccountSecretRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val accountSecret = accountSecretRepository.findByEmail(username) ?: throw UsernameNotFoundException("User not found")
        val account = accountRepository.findByEmail(username)!!
        return User().apply {
            id = account.id
            email = username
            passwordHash = accountSecret.passwordHash
            avatarUrl = account.avatarUrl
        }
    }
}