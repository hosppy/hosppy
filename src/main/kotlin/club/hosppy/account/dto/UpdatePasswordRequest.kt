package club.hosppy.account.dto

import lombok.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class UpdatePasswordRequest {
    val userId: @NotBlank String? = null
    val password: @NotBlank @Min(6) String? = null
}