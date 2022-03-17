package club.hosppy.hospital.config;

import club.hosppy.common.config.HosppyRestConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HosppyRestConfig.class)
public class AppConfig {
}
