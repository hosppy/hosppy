package club.hosppy.account.dto

import club.hosppy.common.validation.PhoneNumber
import org.springframework.util.StringUtils
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Email

class CreateAccountRequest {
    val name: String? = null
    val email: @Email(message = "Invalid email") String = ""

    @PhoneNumber
    val phoneNumber: String? = null
    val isValidRequest: @AssertTrue(message = "Empty request") Boolean
        get() = StringUtils.hasText(email)
}