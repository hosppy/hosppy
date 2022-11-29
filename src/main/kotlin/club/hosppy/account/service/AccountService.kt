package club.hosppy.account.service

import club.hosppy.account.dto.AccountDto
import club.hosppy.account.model.Account
import club.hosppy.account.repo.AccountRepository
import club.hosppy.account.repo.AccountSecretRepository
import club.hosppy.common.api.ResultCode
import club.hosppy.common.crypto.Sign
import club.hosppy.common.error.ServiceException
import club.hosppy.email.EmailTmpl
import club.hosppy.email.dto.EmailRequest
import club.hosppy.email.service.EmailService
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import lombok.RequiredArgsConstructor
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.ui.ModelMap
import java.time.Instant
import javax.persistence.EntityManager
import kotlin.streams.asSequence

@Service
@RequiredArgsConstructor
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountSecretRepository: AccountSecretRepository,
    private val modelMapper: ModelMapper,
    private val entityManager: EntityManager,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService,
    @Value("\${hosppy.web-domain}") private val webDomain: String
) {
    fun getByEmail(email: String?): AccountDto {
        val account = accountRepository.findAccountByEmail(email)
            ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
        return convertToDto(account)
    }

    fun list(offset: Int, limit: Int): List<AccountDto> {
        val pageRequest: Pageable = PageRequest.of(offset, limit)
        val accountPage = accountRepository.findAll(pageRequest)
        return accountPage.stream().asSequence().map(this::convertToDto).toList()
    }

    fun create(name: String?, email: String, phoneNumber: String?): AccountDto {
        val account = Account(
            name = name ?: "",
            phoneNumber = phoneNumber,
            email = email,
            avatarUrl = "",
            memberSince = Instant.now()
        )
        if (email.isNotBlank()) {
            val foundAccount = accountRepository.findAccountByEmail(email)
            if (foundAccount != null) {
                sendActivateEmail(foundAccount)
                return convertToDto(foundAccount)
            }
        }
        try {
            accountRepository.save(account)
        } catch (e: Exception) {
            throw ServiceException(ResultCode.UNABLE_CREATE_ACCOUNT, e)
        }
        if (email.isNotBlank()) {
            sendActivateEmail(account)
        }
        return convertToDto(account)
    }

    fun sendActivateEmail(account: Account) {
        val subject = "Activate your Hosppy account"
        val token = createToken(account.id, account.email)
        val link = "$webDomain/activate/$token"
        val model = ModelMap()
        model.addAttribute("name", account.name)
        model.addAttribute("link", link)
        val emailRequest = EmailRequest(
            to = account.email,
            name = account.name,
            subject = subject,
            tmpl = EmailTmpl.ACTIVATE_ACCOUNT,
            params = model
        )
        try {
            emailService.sendAsync(emailRequest)
        } catch (e: Exception) {
            throw ServiceException(ResultCode.UNABLE_SEND_EMAIL, e)
        }
    }

    private fun createToken(userId: Int?, email: String): String? {
        return try {
            // TODO config signing secret
            Sign.generateEmailConfirmationToken(userId, email, "signing")
        } catch (e: Exception) {
            throw ServiceException(ResultCode.UNABLE_CREATE_TOKEN, e)
        }
    }

    fun update(newAccountDto: AccountDto): AccountDto {
        val newAccount = convertToModel(newAccountDto)
        val existingAccount = accountRepository.findAccountByEmail(newAccount.email)
            ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
        entityManager.detach(existingAccount)

        // TODO update account
        existingAccount.name = newAccount.name
        existingAccount.phoneNumber = newAccount.phoneNumber
        existingAccount.avatarUrl = newAccount.avatarUrl
        accountRepository.save(existingAccount)
        return convertToDto(newAccount)
    }

    fun updatePassword(userId: String?, password: String?) {
        val pwdHash = passwordEncoder.encode(password)
        val affected = accountSecretRepository.updatePasswordHashById(pwdHash, userId)
        if (affected == 0) {
            throw ServiceException(ResultCode.USER_NOT_FOUND)
        }
    }

    fun sendResetEmail(userId: String?, email: String?, name: String?, subject: String?) {}
    fun changeEmailAndActivateAccount(email: String?, userId: String?) {
        val affected = accountRepository.updateEmailAndActivateById(email, userId)
        if (affected == 0) {
            throw ServiceException(ResultCode.USER_NOT_FOUND)
        }
    }

    fun activateAccount(token: String?, password: String?) {
        val jwt = decodeActivateToken(token)
        val email = jwt.getClaim(Sign.CLAIM_EMAIL).`as`(String::class.java)
        val userId = jwt.getClaim(Sign.CLAIM_USER_ID).`as`(Int::class.java)
        val foundAccount = accountRepository.findAccountById(userId)
            ?: throw ServiceException(ResultCode.USER_UNREGISTERED)
        if (foundAccount.confirmedAndActive) {
            throw ServiceException(ResultCode.USER_ALREADY_ACTIVATED)
        }
        foundAccount.email = email
        foundAccount.confirmedAndActive = true
        accountRepository.save(foundAccount)
        val foundAccountSecret = accountSecretRepository.findAccountSecretById(userId)!!
        foundAccountSecret.passwordHash = passwordEncoder.encode(password)
        accountSecretRepository.save(foundAccountSecret)
    }

    fun verifyActivateToken(token: String?) {
        val jwt = decodeActivateToken(token)
        val userId = jwt.getClaim(Sign.CLAIM_USER_ID).`as`(Int::class.java)
        val foundAccount = accountRepository.findAccountById(userId)
        if (foundAccount != null && foundAccount.confirmedAndActive) {
            throw ServiceException(ResultCode.USER_ALREADY_ACTIVATED)
        }
    }

    fun decodeActivateToken(token: String?): DecodedJWT {
        return try {
            Sign.verify(token, "signing")
        } catch (e: JWTVerificationException) {
            throw ServiceException(ResultCode.INVALID_TOKEN)
        }
    }

    private fun convertToDto(account: Account?): AccountDto {
        return modelMapper.map(account, AccountDto::class.java)
    }

    private fun convertToModel(accountDto: AccountDto): Account {
        return modelMapper.map(accountDto, Account::class.java)
    }
}