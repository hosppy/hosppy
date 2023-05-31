package club.hosppy.account.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UpdatePasswordRequest {
    val userId: @NotNull Int = 0
    val password: @NotBlank @Min(6) String = ""
}