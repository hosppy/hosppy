package club.hosppy.email.config

import club.hosppy.email.EmailConstant
import com.aliyun.dm20151123.Client
import com.aliyun.teaopenapi.models.Config
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@RequiredArgsConstructor
@Configuration
@EnableAsync
class AppConfig {
    @Value("\${hosppy.aliyun-access-key}")
    private val aliyunAccessKey: String? = null

    @Value("\${hosppy.aliyun-access-secret}")
    private val aliyunAccessSecret: String? = null
    @Bean
    @Throws(Exception::class)
    fun client(): Client {
        val config = Config()
            .setAccessKeyId(aliyunAccessKey)
            .setAccessKeySecret(aliyunAccessSecret)
            .setRegionId(EmailConstant.ALIYUN_REGION_ID)
            .setEndpoint(EmailConstant.ALIYUN_ENDPOINT)
        return Client(config)
    }

    @Bean(ASYNC_EXECUTOR_NAME)
    fun asyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 3
        executor.maxPoolSize = 5
        executor.queueCapacity = 100
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.setThreadNamePrefix("AsyncThread-")
        executor.initialize()
        return executor
    }

    companion object {
        const val ASYNC_EXECUTOR_NAME = "asyncExecutor"
    }
}