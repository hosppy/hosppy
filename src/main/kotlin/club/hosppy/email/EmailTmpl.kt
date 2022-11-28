package club.hosppy.email

import lombok.AllArgsConstructor
import lombok.Getter

@Getter
@AllArgsConstructor
enum class EmailTmpl(val fileName: String) {
    ACTIVATE_ACCOUNT("activate_account");
}