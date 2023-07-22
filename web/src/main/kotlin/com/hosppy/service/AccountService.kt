package com.hosppy.service

import com.hosppy.common.api.ResultCode
import com.hosppy.common.error.ServiceException
import com.hosppy.common.utils.encodePassword
import com.hosppy.models.*
import com.hosppy.plugins.dbQuery
import org.apache.commons.codec.binary.Hex
import org.jetbrains.exposed.dao.id.EntityID
import java.security.MessageDigest

class AccountService(private val mailService: MailService, private val webDomain: String) {

    suspend fun list() = dbQuery {
        Account.all().map(Account::toDto)
    }

    suspend fun getByEmail(email: String) = dbQuery {
        Account.find { Accounts.email eq email }.firstOrNull()?.toDto()
            ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
    }

    suspend fun create(
        name: String,
        email: String,
        phoneNumber: String,
        password: String
    ): AccountDto = dbQuery {
        val foundAccount = Account.find { Accounts.email eq email }.firstOrNull()
        if (foundAccount?.confirmedAndActive == true) {
            throw ServiceException(ResultCode.USER_ALREADY_EXISTS)
        }
        val passwordHash = encodePassword(password)
        val updatedAccount = if (foundAccount?.confirmedAndActive == false) {
            foundAccount.passwordHash = passwordHash
            foundAccount
        } else {
            val account = Account.new {
                this.email = email
                this.name = name
                this.passwordHash = passwordHash
            }
            account
        }
        sendActivateMail(updatedAccount)
        updatedAccount.toDto()
    }

    private fun sendActivateMail(account: Account) {
        val subject = "Activate your Hosppy account"
        val token = createToken(account.id, account.email)
        val link = "$webDomain/activate/$token"
        val params = mapOf("name" to account.name, "link" to link)
        val emailRequest = MailRequest(
            to = account.email,
            name = account.name,
            subject = subject,
            tmpl = MailTmpl.ACTIVATE_ACCOUNT,
            params = params
        )
        try {
            mailService.send(emailRequest)
        } catch (e: Exception) {
            throw ServiceException(ResultCode.UNABLE_SEND_EMAIL, e)
        }
    }

    private fun createToken(id: EntityID<Int>, email: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return Hex.encodeHexString(md.digest("$id$email${System.currentTimeMillis()}".toByteArray()))
    }
}