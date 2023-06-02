package club.hosppy.account.repo

import club.hosppy.account.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account?, String?> {
    fun findById(id: Int?): Account?
    fun findByEmail(email: String?): Account?
}