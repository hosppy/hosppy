package club.hosppy.account.event;

import club.hosppy.account.model.Account;
import club.hosppy.common.api.ResultCode;
import club.hosppy.common.crypto.Sign;
import club.hosppy.common.error.ServiceException;
import club.hosppy.email.EmailTmpl;
import club.hosppy.email.client.EmailClient;
import club.hosppy.email.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

/**
 * @author minghu.he
 */
@Component
@RequiredArgsConstructor
public class AccountListener {

    private final EmailClient emailClient;

    @Value("${hosppy.web-domain}")
    private String webDomain;

    @EventListener
    public void sendActivateEmail(AccountCreatedEvent event) {
        Account account = event.getAccount();
        String subject = "Activate your Hosppy account";
        String token = createToken(account.getId(), account.getEmail());

        String link = webDomain + "/activate/" + token;

        ModelMap model = new ModelMap();
        model.addAttribute("name", account.getName());
        model.addAttribute("link", link);
        EmailRequest emailRequest = EmailRequest.builder()
            .to(account.getEmail())
            .name(account.getName())
            .subject(subject)
            .tmpl(EmailTmpl.ACTIVATE_ACCOUNT)
            .params(model)
            .build();

        try {
            emailClient.send(emailRequest);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.UNABLE_SEND_EMAIL, e);
        }
    }

    private String createToken(String userId, String email) {
        try {
            // TODO config signing secret
            return Sign.generateEmailConfirmationToken(userId, email, "signing");
        } catch (Exception e) {
            throw new ServiceException(ResultCode.UNABLE_CREATE_TOKEN, e);
        }
    }
}
