package club.hosppy.account.service;

import club.hosppy.account.dto.AccountDto;
import club.hosppy.account.model.Account;
import club.hosppy.account.model.AccountSecret;
import club.hosppy.account.repo.AccountRepository;
import club.hosppy.account.repo.AccountSecretRepository;
import club.hosppy.common.api.ResultCode;
import club.hosppy.common.crypto.Sign;
import club.hosppy.common.error.ServiceException;
import club.hosppy.email.EmailTmpl;
import club.hosppy.email.client.EmailClient;
import club.hosppy.email.dto.EmailRequest;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountSecretRepository accountSecretRepository;

    private final ModelMapper modelMapper;

    private final EmailClient emailClient;

    private final EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;

    @Value("${hosppy.web-domain}")
    private String webDomain;

    public AccountDto get(String userId) {
        Account account = accountRepository.findAccountById(userId);
        if (account == null) {
            throw new ServiceException(ResultCode.USER_NOT_FOUND);
        }
        return this.convertToDto(account);
    }

    public List<AccountDto> list(int offset, int limit) {
        Pageable pageRequest = PageRequest.of(offset, limit);
        Page<Account> accountPage = accountRepository.findAll(pageRequest);
        return accountPage.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public AccountDto create(String name, String email, String phoneNumber) {
        String emailName = name == null ? "there" : name;
        Account account = Account.builder()
                .name(name == null ? email : name)
                .phoneNumber(phoneNumber)
                .email(email)
                .photoUrl("")
                .memberSince(Instant.now())
                .build();

        if (account.hasEmail()) {
            Account foundAccount = accountRepository.findAccountByEmail(email);
            if (foundAccount != null) {
                this.sendActivateEmail(foundAccount.getId(), email, emailName);
                return this.convertToDto(foundAccount);
            }
        }

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.UNABLE_CREATE_ACCOUNT, e);
        }

        if (account.hasEmail()) {
            this.sendActivateEmail(account.getId(), email, emailName);
        }

        return this.convertToDto(account);
    }

    public AccountDto update(AccountDto newAccountDto) {
        Account newAccount = this.convertToModel(newAccountDto);

        Account existingAccount = accountRepository.findAccountById(newAccount.getId());
        if (existingAccount == null) {
            throw new ServiceException(ResultCode.USER_NOT_FOUND);
        }
        entityManager.detach(existingAccount);

        // TODO update account

        return this.convertToDto(newAccount);
    }

    public void updatePassword(String userId, String password) {
        String pwdHash = passwordEncoder.encode(password);

        int affected = accountSecretRepository.updatePasswordHashById(pwdHash, userId);
        if (affected != 1) {
            throw new ServiceException(ResultCode.USER_NOT_FOUND);
        }
    }

    public void sendActivateEmail(String userId, String email, String name) {
        String subject = "Activate your Hosppy account";
        String token = createToken(userId, email);

        String link = webDomain + "/activate/" + token;

        ModelMap model = new ModelMap();
        model.addAttribute("name", name);
        model.addAttribute("link", link);
        EmailRequest emailRequest = EmailRequest.builder()
                .to(email)
                .name(name)
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

    public void sendResetEmail(String userId, String email, String name, String subject) {

    }

    private String createToken(String userId, String email) {
        try {
            // TODO config signing secret
            return Sign.generateEmailConfirmationToken(userId, email, "signing");
        } catch (Exception e) {
            throw new ServiceException(ResultCode.UNABLE_CREATE_TOKEN, e);
        }
    }

    public void changeEmailAndActivateAccount(String email, String userId) {
        int affected = accountRepository.updateEmailAndActivateById(email, userId);
        if (affected != 1) {
            throw new ServiceException(ResultCode.USER_NOT_FOUND);
        }
    }

    public void activateAccount(String token, String password) {
        DecodedJWT jwt = decodeActivateToken(token);
        String email = jwt.getClaim(Sign.CLAIM_EMAIL).as(String.class);
        String userId = jwt.getClaim(Sign.CLAIM_USER_ID).as(String.class);
        Account foundAccount = accountRepository.findAccountById(userId);
        if (foundAccount == null) {
            throw new ServiceException(ResultCode.USER_UNREGISTERED);
        }

        if (foundAccount.isConfirmedAndActive()) {
            throw new ServiceException(ResultCode.USER_ALREADY_ACTIVATED);
        }

        foundAccount.setEmail(email);
        foundAccount.setConfirmedAndActive(true);
        accountRepository.save(foundAccount);

        AccountSecret foundAccountSecret = accountSecretRepository.findAccountSecretById(userId);
        foundAccountSecret.setPasswordHash(passwordEncoder.encode(password));
        accountSecretRepository.save(foundAccountSecret);
    }

    public void verifyActivateToken(String token) {
        DecodedJWT jwt = decodeActivateToken(token);
        String userId = jwt.getClaim(Sign.CLAIM_USER_ID).as(String.class);
        Account foundAccount = accountRepository.findAccountById(userId);
        if (foundAccount != null && foundAccount.isConfirmedAndActive()) {
            throw new ServiceException(ResultCode.USER_ALREADY_ACTIVATED);
        }
    }

    public DecodedJWT decodeActivateToken(String token) {
        try {
            return Sign.verify(token, "signing");
        } catch (JWTVerificationException e) {
            throw new ServiceException(ResultCode.INVALID_TOKEN);
        }
    }

    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    private Account convertToModel(AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }
}
