package club.hosppy.common.api

import javax.servlet.http.HttpServletResponse

enum class ResultCode(
    val code: Int = HttpServletResponse.SC_BAD_REQUEST,
    val type: String,
    val message: String,
) {
    USER_ALREADY_EXISTS(
        HttpServletResponse.SC_BAD_REQUEST,
        "UserExists",
        "The user already exists"
    ),
    USER_UNREGISTERED(HttpServletResponse.SC_BAD_REQUEST, "Unregistered", "The user not registered"),
    USER_NOT_FOUND(
        HttpServletResponse.SC_NOT_FOUND,
        "NotFound",
        "User with specified id is not found"
    ),
    INVALID_TOKEN(HttpServletResponse.SC_BAD_REQUEST, "InvalidToken", "Invalid token"),
    NO_SIGNING_TOKEN(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
        "NoSigningToken",
        "No signing token present"
    ),
    UNABLE_SEND_EMAIL(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
        "UnableSendEmail",
        "Unable to send email"
    ),
    UNABLE_CREATE_TOKEN(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
        "UnableCreateToken",
        "Could not create token"
    ),
    UNABLE_CREATE_ACCOUNT(
        HttpServletResponse.SC_BAD_REQUEST,
        "UnableCreateAccount",
        "Could not create account"
    ),
    BAD_REQUEST(
        HttpServletResponse.SC_BAD_REQUEST,
        "IllegalArgument",
        "Bad Request"
    ),
    NOT_FOUND(
        HttpServletResponse.SC_NOT_FOUND,
        "NotFound",
        "404 Not Found"
    ),
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "InternalServerError", "Internal Server Error");
}