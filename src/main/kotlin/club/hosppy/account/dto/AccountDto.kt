package club.hosppy.account.dto

import club.hosppy.common.validation.PhoneNumber
import java.time.Instant
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class AccountDto {
    var id: @NotBlank Int? = null
    var name: String? = null
    var email: @Email(message = "Invalid email") String? = null
    var confirmedAndActive = false
    var memberSince: @NotNull Instant? = null

    var phoneNumber: @PhoneNumber String? = null
    var avatarUrl: @NotEmpty String? = null
}