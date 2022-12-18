package club.hosppy.account.model

import org.hibernate.Hibernate
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    var name: String = ""
    var email: String = ""
    var confirmedAndActive: Boolean = false
    var memberSince: Instant = Instant.now()
    var phoneNumber: String? = null
    var avatarUrl: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val account = other as Account
        return id != null && id == account.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

}