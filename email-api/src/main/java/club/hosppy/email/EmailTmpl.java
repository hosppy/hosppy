package club.hosppy.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTmpl {

    ACTIVATE_ACCOUNT("activate_account");

    private final String name;
}
