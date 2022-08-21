package club.hosppy.account.event;

import club.hosppy.account.model.Account;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author minghu.he
 */
@Getter
public class AccountCreatedEvent extends ApplicationEvent {

    private final Account account;

    public AccountCreatedEvent(Object source, Account account) {
        super(source);
        this.account = account;
    }
}
