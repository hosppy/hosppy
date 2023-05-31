package club.hosppy.account.dto

import javax.validation.constraints.Size

/**
 * @author minghu.he
 */
class ActivateAccountRequest {
    val password: @Size(min = 6, max = 16) String? = null
}