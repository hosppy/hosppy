package club.hosppy.account.model

import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Account(
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    var id: String? = null,
    var name: String = "",
    var email: String = "",
    var confirmedAndActive: Boolean = false,
    var memberSince: Instant? = null,
    var phoneNumber: String? = null,
    var photoUrl: String? = null,
    var support: Boolean = false
) {
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