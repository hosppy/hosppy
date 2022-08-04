package club.hosppy.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
@AllArgsConstructor
public enum ResultCode {

    USER_ALREADY_ACTIVATED(HttpServletResponse.SC_BAD_REQUEST, "Activated", "The user already activated"),

    USER_UNREGISTERED(HttpServletResponse.SC_BAD_REQUEST, "NotRegistered", "The user not registered"),

    USER_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "NotFound", "User with specified id is not found"),

    INVALID_TOKEN(HttpServletResponse.SC_BAD_REQUEST, "InvalidToken", "Invalid token"),

    NO_SIGNING_TOKEN(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "NoSigningToken", "No signing token present"),

    UNABLE_SEND_EMAIL(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UnableSendEmail", "Unable to send email"),

    UNABLE_CREATE_TOKEN(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "UnableCreateToken", "Could not create token"),

    UNABLE_CREATE_ACCOUNT(HttpServletResponse.SC_BAD_REQUEST, "UnableCreateAccount", "Could not create account"),

    BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, "IllegalArgument", "Bad Request"),

    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "NotFound", "404 Not Found"),

    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "InternalServerError", "Internal Server Error");

    final int code;

    final String status;

    final String message;
}
