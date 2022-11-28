package club.hosppy.email.dto

import club.hosppy.email.EmailTmpl
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class EmailRequest(
    val to: @NotBlank(message = "Please provide an email") String,
    val subject: @NotBlank(message = "Please provide a subject") String,
    val name: String,
    val tmpl: @NotNull EmailTmpl,
    val params: Map<String, Any>? = null
)