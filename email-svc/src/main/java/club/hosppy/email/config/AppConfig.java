package club.hosppy.email.config;

import club.hosppy.common.config.HosppyRestConfig;
import club.hosppy.email.EmailConstant;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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
    public IAcsClient acsClient() {
        IClientProfile profile = DefaultProfile.getProfile(EmailConstant.ALIYUN_REGION_ID, aliyunAccessKey, aliyunAccessSecret);
        return new DefaultAcsClient(profile);
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
