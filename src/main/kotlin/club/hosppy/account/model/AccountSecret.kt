package club.hosppy.account.model

import lombok.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
class AccountSecret {
    @Id
    var id: String? = null
    var passwordHash: String? = null
    var email: String? = null
    var confirmedAndActive = false
}