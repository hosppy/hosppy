package club.hosppy.account.controller;

import club.hosppy.account.dto.*;
import club.hosppy.account.service.AccountService;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final ILogger logger = SLoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @GetMapping
    public List<AccountDto> listAccounts(@RequestParam(defaultValue = "0") @Min(0) int offset,
                                         @RequestParam(defaultValue = "10") @Min(0) int limit) {
        return accountService.list(offset, limit);
    }

    @GetMapping("/{userId}")
    public AccountDto getAccount(@PathVariable String userId) {
        return accountService.get(userId);
    }

    @PostMapping
    public AccountDto createAccount(@RequestBody @Valid CreateAccountRequest request) {
        return accountService.create(request.getName(), request.getEmail(), request.getPhoneNumber());
    }

    @PutMapping("/password")
    public void updatePassword(@RequestBody @Valid UpdatePasswordRequest request) {
        accountService.updatePassword(request.getUserId(), request.getPassword());
    }

    @PostMapping("/email")
    public void changeEmail(EmailConfirmation request) {
        accountService.changeEmailAndActivateAccount(request.getEmail(), request.getUserId());
    }

    @GetMapping("/activate/{token}")
    public void verifyActivateToken(@PathVariable String token) {
        accountService.verifyActivateToken(token);
    }

    @PostMapping("/activate/{token}")
    public void activateAccount(@PathVariable String token, @RequestBody @Valid ActivateAccountRequest request) {
        accountService.activateAccount(token, request.getPassword());
    }
}
