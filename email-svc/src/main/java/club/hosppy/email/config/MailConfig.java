package club.hosppy.email.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MailConfig {

    @Value("${hosppy.mail-from}")
    private String from;

    @Value("${hosppy.mail-from-name}")
    private String fromName;
}
