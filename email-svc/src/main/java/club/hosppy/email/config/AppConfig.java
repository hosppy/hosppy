package club.hosppy.email.config;

import club.hosppy.common.config.HosppyRestConfig;
import club.hosppy.email.EmailConstant;
import com.aliyun.dm20151123.Client;
import com.aliyun.teaopenapi.models.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Configuration
@EnableAsync
@Import(HosppyRestConfig.class)
public class AppConfig {

    public static final String ASYNC_EXECUTOR_NAME = "asyncExecutor";

    @Value("${hosppy.aliyun-access-key}")
    private String aliyunAccessKey;

    @Value("${hosppy.aliyun-access-secret}")
    private String aliyunAccessSecret;

    @Bean
    public Client client() throws Exception {
        Config config = new Config()
                .setAccessKeyId(aliyunAccessKey)
                .setAccessKeySecret(aliyunAccessSecret)
                .setRegionId(EmailConstant.ALIYUN_REGION_ID)
                .setEndpoint(EmailConstant.ALIYUN_ENDPOINT);
        return new Client(config);
    }

    @Bean(ASYNC_EXECUTOR_NAME)
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}
