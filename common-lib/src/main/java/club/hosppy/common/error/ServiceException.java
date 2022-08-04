package club.hosppy.common.error;

import club.hosppy.common.api.ResultCode;
import lombok.Getter;

public class ServiceException extends RuntimeException {

    @Getter
    private final ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }
}
