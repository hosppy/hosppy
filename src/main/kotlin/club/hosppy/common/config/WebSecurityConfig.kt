package club.hosppy.common.config

import JSONAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession


@Configuration
@EnableRedisHttpSession
@EnableWebSecurity
class WebSecurityConfig(
    val authConfiguration: AuthenticationConfiguration
) {

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authConfiguration.getAuthenticationManager()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/api/accounts/activate/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(JSONAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter::class.java)
            .formLogin()
            .loginProcessingUrl("/api/authentication")
            .failureHandler(CustomAuthenticationFailureHandler())
            .permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint())
            .and()
            .build()
    }

    @Bean
    fun jsonAuthenticationFilter(authenticationManager: AuthenticationManager): JSONAuthenticationFilter {
        return JSONAuthenticationFilter(authenticationManager)
    }
}