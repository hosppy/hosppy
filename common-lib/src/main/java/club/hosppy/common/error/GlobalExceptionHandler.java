package club.hosppy.common.error;

import club.hosppy.common.api.BaseResponse;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    static final ILogger logger = SLoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<BaseResponse> handleError(ServiceException e) {
        if (e.getCause() != null) {
            logger.info("Service Exception", e);
        }
        BaseResponse error = BaseResponse.builder()
            .code(e.getResultCode().getCode())
            .status(e.getResultCode().getStatus())
            .message(e.getResultCode().getMessage())
            .build();
        return ResponseEntity.status(error.getCode()).body(error);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse> handleError(Throwable e) {
        logger.error("Internal Server Error", e);
        BaseResponse error = BaseResponse.builder()
            .code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            .status("InternalServerError")
            .message("Internal Server Error")
            .build();
        return ResponseEntity.internalServerError().body(error);
    }
}
