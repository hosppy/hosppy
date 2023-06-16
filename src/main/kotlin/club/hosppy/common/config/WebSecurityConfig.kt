package club.hosppy.common.config

import JSONAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession


@Configuration
@EnableRedisHttpSession
@EnableWebSecurity
class WebSecurityConfig(
    private val userDetailsService: UserDetailsService
) {

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
            .addFilter(jsonAuthenticationFilter())
            .exceptionHandling()
            .and()
            .build()
    }

    @Bean
    fun jsonAuthenticationFilter(): JSONAuthenticationFilter {
        val filter = JSONAuthenticationFilter()
        filter.setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/api/authentication", "POST"))
        filter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler())
        filter.setAuthenticationManager(authenticationManager())
        return filter
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val daoAuthenticationProvider = DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        val providerManager = ProviderManager(daoAuthenticationProvider);
        return providerManager
    }
}