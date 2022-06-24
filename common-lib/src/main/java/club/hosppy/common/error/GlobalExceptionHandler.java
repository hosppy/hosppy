package club.hosppy.common.error;

import club.hosppy.common.api.BaseResponse;
import club.hosppy.common.api.ResultCode;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    static final ILogger logger = SLoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<BaseResponse> HandleError(ServiceException e) {
        logger.info("Service Exception", e);
        BaseResponse error = BaseResponse.builder()
                .code(e.getResultCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(error.getCode().value()).body(error);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse> handleError(Throwable e) {
        logger.error("Internal Server Error", e);
        BaseResponse error = BaseResponse.builder()
                .code(ResultCode.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(error);
    }
}
