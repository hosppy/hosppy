package club.hosppy.common.error

import club.hosppy.common.api.ResultCode

class ServiceException : RuntimeException {
    val resultCode: ResultCode

    constructor(resultCode: ResultCode) {
        this.resultCode = resultCode
    }

    constructor(resultCode: ResultCode, cause: Throwable) : super(cause) {
        this.resultCode = resultCode
    }
}