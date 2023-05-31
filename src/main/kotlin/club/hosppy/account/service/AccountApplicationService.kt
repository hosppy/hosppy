package club.hosppy.account.service

import club.hosppy.account.dto.AccountDto
import club.hosppy.account.model.Account
import club.hosppy.account.repo.AccountRepository
import club.hosppy.account.repo.AccountSecretRepository
import club.hosppy.account.service.domain.AccountService
import club.hosppy.common.api.ResultCode
import club.hosppy.common.crypto.Sign
import club.hosppy.common.error.ServiceException
import club.hosppy.email.EmailTmpl
import club.hosppy.email.dto.EmailRequest
import club.hosppy.email.service.EmailService
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import kotlin.streams.asSequence

@Service
class AccountApplicationService(
    private val accountRepository: AccountRepository,
    private val accountSecretRepository: AccountSecretRepository,
    private val modelMapper: ModelMapper,
    private val entityManager: EntityManager,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService,
    private val accountService: AccountService,
    @Value("\${hosppy.web-domain}") private val webDomain: String,
) {
    fun getByEmail(email: String): AccountDto {
        val account = accountRepository.findByEmail(email)
            ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
        return convertToDto(account)
    }

    fun list(page: Int, size: Int): List<AccountDto> {
        val pageRequest: Pageable = PageRequest.of(page, size)
        val accountPage = accountRepository.findAll(pageRequest)
        return accountPage.stream().asSequence().map(this::convertToDto).toList()
    }

    fun create(
        name: String,
        email: String,
        phoneNumber: String?,
        password: String,
    ): AccountDto {
        val foundAccount = accountRepository.findByEmail(email)
        if (foundAccount?.confirmedAndActive == true) {
            throw ServiceException(ResultCode.USER_ALREADY_EXISTS)
        }
        if (foundAccount == null) {
            val account = accountService.create(name, email)
            accountRepository.save(account)
            sendActivateEmail(account)
            return convertToDto(account)
        }
        val accountSecret = accountSecretRepository.findByEmail(email)
        accountService.modifyPassword(accountSecret, password)
        sendActivateEmail(foundAccount)
        return convertToDto(foundAccount)
    }

    fun sendActivateEmail(account: Account) {
        val subject = "Activate your Hosppy account"
        val token = createToken(account.id, account.email)
        val link = "$webDomain/activate/$token"
        val params = mapOf("name" to account.name, "link" to link)
        val emailRequest = EmailRequest(
            to = account.email,
            name = account.name,
            subject = subject,
            tmpl = EmailTmpl.ACTIVATE_ACCOUNT,
            params = params
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
        val existingAccount = accountRepository.findByEmail(newAccount.email)
            ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
        entityManager.detach(existingAccount)

        // TODO update account
        existingAccount.name = newAccount.name
        existingAccount.phoneNumber = newAccount.phoneNumber
        existingAccount.avatarUrl = newAccount.avatarUrl
        accountRepository.save(existingAccount)
        return convertToDto(newAccount)
    }

    fun updatePassword(userId: Int, password: String) {
        val account = accountSecretRepository.findByIdOrNull(userId) ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
        accountService.modifyPassword(account, password)
        accountSecretRepository.save(account)
    }

    fun changeEmail(email: String, userId: String) {
        val account = accountRepository.findByIdOrNull(userId) ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
        account.email = email
        account.confirmedAndActive = true
        accountRepository.save(account)
    }

    fun activateAccount(token: String) {
        val jwt = decodeActivateToken(token)
        val userId = jwt.getClaim(Sign.CLAIM_USER_ID).`as`(Int::class.java)
        val foundAccount = accountRepository.findById(userId) ?: throw ServiceException(ResultCode.INVALID_TOKEN)
        if (!foundAccount.confirmedAndActive) {
            foundAccount.confirmedAndActive = true
            accountRepository.save(foundAccount)
        }
    }

    fun decodeActivateToken(token: String): DecodedJWT {
        return try {
            Sign.verify(token, "signing")
        } catch (e: JWTVerificationException) {
            throw ServiceException(ResultCode.INVALID_TOKEN)
        }
    }

    fun authenticate(email: String, password: String) {
        val accountSecret = accountSecretRepository.findByEmail(email) ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
        if (!accountService.matchPassword(accountSecret, password)) {
            throw ServiceException(ResultCode.AUTHENTICATE_FAILED)
        }
        // TODO

    }

    private fun convertToDto(account: Account?): AccountDto = modelMapper.map(account, AccountDto::class.java)
    private fun convertToModel(accountDto: AccountDto): Account = modelMapper.map(accountDto, Account::class.java)
}