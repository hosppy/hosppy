package club.hosppy.common.config

import club.hosppy.common.api.BaseResponse
import club.hosppy.common.api.ResultCode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationFailureHandler : AuthenticationFailureHandler {
    val objectMapper = ObjectMapper()

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.contentType = "application/json;charset=utf-8"
        response.status = HttpStatus.UNAUTHORIZED.value()
        val body = BaseResponse(
            code = ResultCode.AUTHENTICATE_FAILED.code,
            type = ResultCode.AUTHENTICATE_FAILED.type,
            message = ResultCode.AUTHENTICATE_FAILED.message
        )
        objectMapper.writeValue(response.writer, body)
    }
}