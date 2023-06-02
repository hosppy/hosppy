package club.hosppy.account.repo

import club.hosppy.account.model.AccountSecret
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountSecretRepository : JpaRepository<AccountSecret, Int> {
    fun findByEmail(email: String): AccountSecret?
}