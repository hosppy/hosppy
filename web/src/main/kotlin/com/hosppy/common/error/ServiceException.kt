package com.hosppy.common.error

import com.hosppy.common.api.ResultCode

data class ServiceException(
    val resultCode: ResultCode,
    override val cause: Throwable?,
) : RuntimeException(resultCode.message, cause) {
    constructor(resultCode: ResultCode) : this(resultCode, null)
}