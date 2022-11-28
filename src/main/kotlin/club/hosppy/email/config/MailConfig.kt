package club.hosppy.email.config

import lombok.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
@Getter
class MailConfig {
    @Value("\${hosppy.mail-from}")
    lateinit var from: String

    @Value("\${hosppy.mail-from-name}")
    lateinit var fromName: String
}