package club.hosppy.account.dto

import lombok.Data
import javax.validation.constraints.Size

/**
 * @author minghu.he
 */
@Data
class ActivateAccountRequest {
    val password: @Size(min = 6, max = 16) String? = null
}