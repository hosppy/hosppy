package club.hosppy.account.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class AccountSecret {

    @Id
    private String id;

    private String passwordHash;

    private String email;

    private boolean confirmedAndActive;
}
