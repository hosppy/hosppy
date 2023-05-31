package club.hosppy.account.repo

import club.hosppy.account.model.AccountSecret
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AccountSecretRepository : JpaRepository<AccountSecret, Int> {
    fun findByEmail(email: String): AccountSecret

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AccountSecret account SET account.passwordHash = :passwordHash WHERE account.email = :email")
    @Transactional
    fun updatePasswordHashByEmail(email: String)
}