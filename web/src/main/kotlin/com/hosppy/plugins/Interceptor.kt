package com.hosppy.plugins

import com.hosppy.common.api.INTERNAL_SERVER_ERROR
import com.hosppy.common.error.ServiceException
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.response.*
import io.ktor.util.logging.*

internal val LOGGER = KtorSimpleLogger("com.hosppy.plugins.Interceptor")

val ExceptionInterceptor = createApplicationPlugin("ExceptionInterceptor") {
    on(CallFailed) { call, cause ->
        when (cause) {
            is ServiceException -> {
                LOGGER.warn("Service exception occurred", cause)
                call.respond(cause.resultCode)
            }

            else -> {
                LOGGER.error(cause)
                call.respond(INTERNAL_SERVER_ERROR)
            }
        }
    }
}

fun Application.configureInterceptor() {
    install(ExceptionInterceptor)
}