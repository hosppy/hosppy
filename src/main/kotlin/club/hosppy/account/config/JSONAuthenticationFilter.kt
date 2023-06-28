package club.hosppy.account.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JSONAuthenticationFilter : UsernamePasswordAuthenticationFilter() {
    private val objectMapper = ObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        if (MediaType.APPLICATION_JSON_VALUE == request.contentType) {
            if (!request.method.equals(HttpMethod.POST.name)) {
                throw AuthenticationServiceException("Authentication method not supported: " + request.method);
            }
            try {
                val loginRequest = objectMapper.readValue(request.inputStream, LoginRequest::class.java)
                val authRequest = UsernamePasswordAuthenticationToken(loginRequest.username?.trim(), loginRequest.password?.trim())
                setDetails(request, authRequest)
                return this.authenticationManager.authenticate(authRequest)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        return super.attemptAuthentication(request, response)
    }

    private class LoginRequest {
        var username: String? = null
        var password: String? = null
    }
}