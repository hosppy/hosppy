package club.hosppy.account.dto

import javax.validation.constraints.NotBlank

class AuthenticationRequest {
    val email: @NotBlank String = ""
    val password: @NotBlank String = ""
}