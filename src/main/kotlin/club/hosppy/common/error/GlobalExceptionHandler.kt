package club.hosppy.common.error

import club.hosppy.common.api.BaseResponse
import com.github.structlog4j.ILogger
import com.github.structlog4j.SLoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException::class)
    fun handleError(e: ServiceException): ResponseEntity<BaseResponse> {
        if (e.cause != null) {
            logger.info("Service Exception", e)
        }
        val error = BaseResponse(
            code = e.resultCode.code,
            status = e.resultCode.status,
            message = e.resultCode.message
        )
        return ResponseEntity.status(error.code).body(error)
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleError(e: Throwable?): ResponseEntity<BaseResponse> {
        logger.error("Internal Server Error", e)
        val error = BaseResponse(
            code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            status = "InternalServerError",
            message = "Internal Server Error"
        )
        return ResponseEntity.internalServerError().body(error)
    }

    companion object {
        @JvmField
        val logger: ILogger = SLoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}