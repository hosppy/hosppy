package com.hosppy.common.api

import kotlinx.serialization.Serializable

@Serializable
data class ResultCode(
    val code: Int,
    val error: String,
    val message: String,
) {
    companion object {
        val BAD_REQUEST = ResultCode(400, "BadRequest", "Illegal arguments")
        val NOT_FOUND = ResultCode(404, "NotFound", "404 Not Found")
        val INTERNAL_SERVER_ERROR = ResultCode(500, "InternalServerError", "Internal Server Error")
        val BAD_CREDENTIALS = ResultCode(401, "BadCredentials", "The username or password is incorrect")
        val USER_ALREADY_EXISTS = ResultCode(400, "UserExists", "The user already exists")
        val USER_UNREGISTERED = ResultCode(400, "Unregistered", "The user not registered")
        val USER_NOT_FOUND = ResultCode(404, "NotFound", "User with specified id is not found")
        val INVALID_TOKEN = ResultCode(400, "InvalidToken", "Invalid token")
        val NO_SIGNING_TOKEN = ResultCode(500, "NoSigningToken", "No signing token present")
        val UNABLE_SEND_EMAIL = ResultCode(500, "UnableSendEmail", "Unable to send email")
        val UNABLE_CREATE_TOKEN = ResultCode(500, "UnableCreateToken", "Could not create token")
        val UNABLE_CREATE_ACCOUNT = ResultCode(400, "UnableCreateAccount", "Could not create account")
    }
}
