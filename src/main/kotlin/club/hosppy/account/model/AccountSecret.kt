package club.hosppy.account.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "account")
class AccountSecret {
    @Id
    var id: Int? = null
    var passwordHash: String? = null
    var email: String? = null
    var confirmedAndActive = false
}