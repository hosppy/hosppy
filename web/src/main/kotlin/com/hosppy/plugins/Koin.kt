package com.hosppy.plugins

import com.aliyun.dm20151123.Client
import com.aliyun.teaopenapi.models.Config
import com.hosppy.common.constant.ALIYUN_ENDPOINT
import com.hosppy.common.constant.ALIYUN_REGION_ID
import com.hosppy.models.MailProperty
import com.hosppy.service.AccountService
import com.hosppy.service.MailService
import freemarker.template.Configuration
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    val aliyunAccessKey = environment.config.property("hosppy.aliyunAccessKey").getString()
    val aliyunAccessSecret = environment.config.property("hosppy.aliyunAccessSecret").getString()
    val mailFrom = environment.config.property("hosppy.mailFrom").getString()
    val mailFromName = environment.config.property("hosppy.mailFromName").getString()
    val webDomain = environment.config.property("hosppy.webDomain").getString()

    val appModule = module {
        single {
            Client(
                Config()
                    .setAccessKeyId(aliyunAccessKey)
                    .setAccessKeySecret(aliyunAccessSecret)
                    .setRegionId(ALIYUN_REGION_ID)
                    .setEndpoint(ALIYUN_ENDPOINT)
            )
        }
        single { MailProperty(mailFrom, mailFromName) }
        single { AccountService(get(), webDomain) }
        single {
            Configuration(Configuration.getVersion()).apply {
                setClassForTemplateLoading(javaClass, "/templates")
                recognizeStandardFileExtensions = true
                defaultEncoding = "UTF-8";
            }
        }
        single { MailService(get(), get(), get()) }
    }
    koin {
        slf4jLogger()
        modules(appModule)
    }
}