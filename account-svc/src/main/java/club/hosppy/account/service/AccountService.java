package club.hosppy.account.service;

import club.hosppy.account.AccountConstant;
import club.hosppy.account.dto.AccountDto;
import club.hosppy.account.model.Account;
import club.hosppy.account.repo.AccountRepository;
import club.hosppy.account.repo.AccountSecretRepository;
import club.hosppy.common.api.ResultCode;
import club.hosppy.common.crypto.Sign;
import club.hosppy.common.error.ServiceException;
import club.hosppy.email.client.EmailClient;
import club.hosppy.email.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
            throw new ServiceException(ResultCode.NOT_FOUND, String.format("User with %s not found", userId));
        }
        return this.convertToDto(account);
    }

    public List<AccountDto> list(int offset, int limit) {
        Pageable pageRequest = PageRequest.of(offset, limit);
        Page<Account> accountPage = accountRepository.findAll(pageRequest);
        return accountPage.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public AccountDto create(String name, String email, String phoneNumber) {
        if (StringUtils.hasText(email)) {
            Account foundAccount = accountRepository.findAccountByEmail(email);
            if (foundAccount != null) {
                throw new ServiceException("A user with that email already exists. Try a password reset");
            }
        }

        Account account = Account.builder()
                .name(name == null ? email : name)
                .phoneNumber(phoneNumber)
                .email(email)
                .photoUrl("")
                .memberSince(Instant.now())
                .build();

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            String errMsg = "Could not create account";
            throw new ServiceException(errMsg, e);
        }

        if (StringUtils.hasText(email)) {
            String emailName = name == null ? "there" : name;

            this.sendActivateEmail(account.getId(), email, emailName);
        }

        return this.convertToDto(account);
    }

    public AccountDto update(AccountDto newAccountDto) {
        Account newAccount = this.convertToModel(newAccountDto);

        Account existingAccount = accountRepository.findAccountById(newAccount.getId());
        if (existingAccount == null) {
            throw new ServiceException(ResultCode.NOT_FOUND, String.format("User with id %s not found", newAccount.getId()));
        }
        entityManager.detach(existingAccount);

        // TODO unfinished

        return this.convertToDto(newAccount);
    }

    public void updatePassword(String userId, String password) {
        String pwdHash = passwordEncoder.encode(password);

        int affected = accountSecretRepository.updatePasswordHashById(pwdHash, userId);
        if (affected != 1) {
            throw new ServiceException(ResultCode.NOT_FOUND, "User with specified id not found");
        }
    }

    public void sendActivateEmail(String userId, String email, String name) {
        String subject = "Activate your Hosppy account";
        String token = createToken(userId, email);

        String link = webDomain + "/activate/" + token;

        String htmlBody = String.format(AccountConstant.ACTIVATE_ACCOUNT_TMPL, name, link, link, link);

        EmailRequest emailRequest = EmailRequest.builder()
                .to(email)
                .name(name)
                .subject(subject)
                .htmlBody(htmlBody)
                .build();

        try {
            emailClient.send(emailRequest);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Unable to send email", e);
        }
    }

    public void sendResetEmail(String userId, String email, String name, String subject) {

    }

    private String createToken(String userId, String email) {
        try {
            // TODO config signing secret
            return Sign.generateEmailConfirmationToken(userId, email, "signing");
        } catch (Exception e) {
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "Could not create token", e);
        }
    }

    public void changeEmailAndActivateAccount(String email, String userId) {
        int affected = accountRepository.updateEmailAndActivateById(email, userId);
        if (affected != 1) {
            throw new ServiceException(ResultCode.NOT_FOUND, "User with specified id is not found");
        }
    }

    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    private Account convertToModel(AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }
}
