package club.hosppy.account.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author minghu.he
 */
@Data
public class ActivateAccountRequest {

    @Length(min = 6, max = 16)
    private String password;
}
