package club.hosppy.account.service.domain

import club.hosppy.account.model.Account
import club.hosppy.account.model.AccountSecret
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val passwordEncoder: PasswordEncoder
) {

    fun matchPassword(accountSecret: AccountSecret, password: String): Boolean {
        return passwordEncoder.matches(password, accountSecret.passwordHash)
    }

    fun modifyPassword(account: AccountSecret, password: String) {
        account.passwordHash = passwordEncoder.encode(password)
    }

    fun create(name: String, email: String): Account {
        return Account().apply {
            this.name = name
            this.email = email
        }
    }
}