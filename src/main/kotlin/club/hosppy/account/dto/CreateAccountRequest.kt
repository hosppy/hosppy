package club.hosppy.account.dto

import club.hosppy.common.validation.PhoneNumber
import org.springframework.util.StringUtils
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class CreateAccountRequest {
    val name: String = ""

    @Email(message = "Invalid email")
    val email: String = ""

    @NotBlank(message = "Invalid password")
    @Size(min = 2, max = 16)
    val password: String = ""

    @PhoneNumber
    val phoneNumber: String? = null
    val isValidRequest: Boolean
        @AssertTrue(message = "Empty request")
        get() = StringUtils.hasText(email)
}