package club.hosppy.account.dto

import club.hosppy.common.validation.PhoneNumber
import java.time.Instant
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class AccountDto {
    val id: @NotBlank String? = null
    val name: String? = null
    val email: @Email(message = "Invalid email") String? = null
    val confirmedAndActive = false
    val memberSince: @NotNull Instant? = null

    val phoneNumber: @PhoneNumber String? = null
    val avatarUrl: @NotEmpty String? = null
}