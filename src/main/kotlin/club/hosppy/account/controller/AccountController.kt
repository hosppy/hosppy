package club.hosppy.account.controller

import club.hosppy.account.dto.*
import club.hosppy.account.service.AccountService
import com.github.structlog4j.SLoggerFactory
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping
    fun listAccounts(
        @RequestParam(defaultValue = "0") page: @Min(0) Int,
        @RequestParam(defaultValue = "10") size: @Min(0) Int
    ): List<AccountDto?>? {
        return accountService.list(page, size)
    }

    @GetMapping("/{email}")
    fun getAccount(@PathVariable email: String?): AccountDto? {
        return accountService.getByEmail(email)
    }

    @PostMapping
    fun createAccount(@RequestBody request: @Valid CreateAccountRequest): AccountDto? {
        return accountService.create(request.name, request.email, request.phoneNumber)
    }

    @PutMapping("/password")
    fun updatePassword(@RequestBody request: @Valid UpdatePasswordRequest) {
        accountService.updatePassword(request.userId, request.password)
    }

    @PostMapping("/email")
    fun changeEmail(request: EmailConfirmation) {
        accountService.changeEmailAndActivateAccount(request.email, request.userId)
    }

    @GetMapping("/activate/{token}")
    fun verifyActivateToken(@PathVariable token: String?) {
        accountService.verifyActivateToken(token)
    }

    @PostMapping("/activate/{token}")
    fun activateAccount(@PathVariable token: String?, @RequestBody request: @Valid ActivateAccountRequest) {
        accountService.activateAccount(token, request.password)
    }

    companion object {
        private val logger = SLoggerFactory.getLogger(AccountController::class.java)
    }
}