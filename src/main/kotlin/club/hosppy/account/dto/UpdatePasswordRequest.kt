package club.hosppy.account.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class UpdatePasswordRequest {
    val userId: @NotBlank String? = null
    val password: @NotBlank @Min(6) String? = null
}