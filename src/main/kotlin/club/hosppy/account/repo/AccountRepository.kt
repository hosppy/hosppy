package club.hosppy.account.repo

import club.hosppy.account.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AccountRepository : JpaRepository<Account?, String?> {
    fun findAccountById(id: Int?): Account?
    fun findAccountByEmail(email: String?): Account?
    fun findAccountByPhoneNumber(phoneNumber: String?): Account?

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account account SET account.email = :email, account.confirmedAndActive = true WHERE account.id = :id")
    @Transactional
    fun updateEmailAndActivateById(email: String?, id: String?): Int
}