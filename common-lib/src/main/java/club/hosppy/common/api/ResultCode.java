package club.hosppy.common.api;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public enum ResultCode {

    BAD_AUTHENTICATION(HttpServletResponse.SC_BAD_REQUEST, "Bad Authentication"),

    BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, "Bad Request"),

    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 Not Found"),

    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");

    @JsonValue
    final int value;

    final String message;

    public int value() {
        return this.value;
    }

    public String message() {
        return this.message;
    }
}
