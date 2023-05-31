package club.hosppy.account.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class EmailConfirmation {
    lateinit var userId: @NotBlank String
    lateinit var email: @NotEmpty @Email String
}