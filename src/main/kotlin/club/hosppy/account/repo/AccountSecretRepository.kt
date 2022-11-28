package club.hosppy.account.repo

import club.hosppy.account.model.AccountSecret
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AccountSecretRepository : JpaRepository<AccountSecret?, String?> {
    fun findAccountSecretByEmail(email: String?): AccountSecret?
    fun findAccountSecretById(id: String?): AccountSecret?

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AccountSecret account SET account.passwordHash = :passwordHash WHERE account.id = :id")
    @Transactional
    fun updatePasswordHashById(passwordHash: String?, id: String?): Int
}