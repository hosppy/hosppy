import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JSONAuthenticationFilter(authenticationManager: AuthenticationManager?) : UsernamePasswordAuthenticationFilter() {
    private val objectMapper = ObjectMapper()

    init {
        super.setAuthenticationManager(authenticationManager)
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val reader = BufferedReader(InputStreamReader(request.inputStream))
            val json = reader.lines().collect(Collectors.joining())
            val loginRequest = objectMapper.readValue(json, LoginRequest::class.java)
            val authRequest = UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            setDetails(request, authRequest)
            return this.authenticationManager.authenticate(authRequest)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private class LoginRequest {
        var username: String? = null
        var password: String? = null
    }
}