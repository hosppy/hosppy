package club.hosppy.account.dto

import club.hosppy.common.validation.PhoneNumber
import javax.validation.constraints.NotEmpty

class UpdateAccountRequest {
    val name: String = ""
    var phoneNumber: @PhoneNumber String? = null
    var avatarUrl: @NotEmpty String? = null
}
