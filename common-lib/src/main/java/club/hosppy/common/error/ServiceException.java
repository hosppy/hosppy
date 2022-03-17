package club.hosppy.common.error;

import club.hosppy.common.api.ResultCode;
import lombok.Getter;

public class ServiceException extends RuntimeException {

    @Getter
    private final ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        this(resultCode, resultCode.message());
    }

    public ServiceException(String message) {
        this(ResultCode.BAD_REQUEST, message);
    }

    public ServiceException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCode.BAD_REQUEST;
    }

    public ServiceException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }
}
