package com.hosppy.plugins

import com.hosppy.common.api.ResultCode
import com.hosppy.common.error.ServiceException
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.response.*
import io.ktor.util.logging.*

internal val log = KtorSimpleLogger("com.hosppy.plugins.Interceptor")

val ExceptionInterceptor = createApplicationPlugin("ExceptionInterceptor") {
    on(CallFailed) { call, cause ->
        when (cause) {
            is ServiceException -> {
                log.warn("Service exception occurred", cause)
                call.respond(cause.resultCode)
            }

            else -> {
                log.error(cause)
                call.respond(ResultCode.INTERNAL_SERVER_ERROR)
            }
        }
    }
}

fun Application.configureInterceptor() {
    install(ExceptionInterceptor)
}