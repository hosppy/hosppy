package com.hosppy.plugins

import com.hosppy.service.AccountService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    val appModule = module {
        single { AccountService() }
    }
    koin {
        slf4jLogger()
        modules(appModule)
    }
}