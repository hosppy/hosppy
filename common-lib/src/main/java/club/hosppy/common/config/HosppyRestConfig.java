package club.hosppy.common.config;

import club.hosppy.common.error.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({HosppyConfig.class, GlobalExceptionHandler.class})
public class HosppyRestConfig {
}
