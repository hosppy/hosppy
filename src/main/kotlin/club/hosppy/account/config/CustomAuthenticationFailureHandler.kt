package club.hosppy.account.config

import club.hosppy.common.api.BaseResponse
import club.hosppy.common.api.ResultCode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFailureHandler : AuthenticationFailureHandler {
    private val objectMapper = ObjectMapper()

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.status = HttpStatus.UNAUTHORIZED.value()
        val body = BaseResponse(
            code = ResultCode.BAD_CREDENTIALS.code,
            type = ResultCode.BAD_CREDENTIALS.type,
            message = ResultCode.BAD_CREDENTIALS.message
        )
        objectMapper.writeValue(response.writer, body)
    }
}