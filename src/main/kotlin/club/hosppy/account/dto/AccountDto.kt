package club.hosppy.account.dto

import club.hosppy.common.validation.PhoneNumber
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.Instant
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AccountDto {
    val id: @NotBlank String? = null
    val name: String? = null
    val email: @Email(message = "Invalid email") String? = null
    val confirmedAndActive = false
    val memberSince: @NotNull Instant? = null

    @PhoneNumber
    val phoneNumber: String? = null
    val avatarUrl: @NotEmpty String? = null
}