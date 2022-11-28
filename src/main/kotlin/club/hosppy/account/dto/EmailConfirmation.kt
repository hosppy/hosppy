package club.hosppy.account.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class EmailConfirmation {
    val userId: @NotBlank String? = null
    val email: @NotEmpty @Email String? = null
}