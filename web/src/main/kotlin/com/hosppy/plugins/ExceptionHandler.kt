package com.hosppy.plugins

import com.hosppy.common.error.ServiceException
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.response.*

val ExceptionInterceptor = createApplicationPlugin("ExceptionInterceptor") {
    on(CallFailed) { call, cause ->
        when (cause) {
            is ServiceException -> {
                call.respond(cause.resultCode)
            }
        }
    }
}

fun Application.configureExceptionInterceptor() {
    install(ExceptionInterceptor)
}