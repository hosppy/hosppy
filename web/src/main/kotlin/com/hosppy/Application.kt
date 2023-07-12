package com.hosppy

import com.hosppy.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureTemplating()
    configureDatabases(environment.config)
    configureRouting()
    configureKoin()
    configureInterceptor()
}
