package com.hosppy.service

import com.hosppy.common.api.ResultCode
import com.hosppy.common.error.ServiceException
import com.hosppy.models.Account
import com.hosppy.models.Accounts
import com.hosppy.models.toDto
import com.hosppy.plugins.dbQuery

class AccountService {

    suspend fun list() = dbQuery {
        Account.all().map(Account::toDto)
    }

    suspend fun getByEmail(email: String) = dbQuery {
        Account.find { Accounts.email eq email }.firstOrNull()?.toDto()
            ?: throw ServiceException(ResultCode.USER_NOT_FOUND)
    }
}