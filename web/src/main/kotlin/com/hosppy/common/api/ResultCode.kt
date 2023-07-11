package com.hosppy.common.api

enum class ResultCode(
    val code: Int,
    val type: String,
    val message: String,
) {
    BAD_REQUEST(
        400,
        "BadRequest",
        "Illegal arguments"
    ),
    NOT_FOUND(
        404,
        "NotFound",
        "404 Not Found"
    ),
    INTERNAL_SERVER_ERROR(
        500,
        "InternalServerError",
        "Internal Server Error"
    ),
    BAD_CREDENTIALS(
        401,
        "BadCredentials",
        "The username or password is incorrect"
    ),
    USER_ALREADY_EXISTS(
        400,
        "UserExists",
        "The user already exists"
    ),
    USER_UNREGISTERED(
        400,
        "Unregistered",
        "The user not registered"
    ),
    USER_NOT_FOUND(
        404,
        "NotFound",
        "User with specified id is not found"
    ),
    INVALID_TOKEN(
        400,
        "InvalidToken",
        "Invalid token"
    ),
    NO_SIGNING_TOKEN(
        500,
        "NoSigningToken",
        "No signing token present"
    ),
    UNABLE_SEND_EMAIL(
        500,
        "UnableSendEmail",
        "Unable to send email"
    ),
    UNABLE_CREATE_TOKEN(
        500,
        "UnableCreateToken",
        "Could not create token"
    ),
    UNABLE_CREATE_ACCOUNT(
        400,
        "UnableCreateAccount",
        "Could not create account"
    )
    ;
}