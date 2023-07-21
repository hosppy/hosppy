package com.hosppy.plugins

import com.hosppy.routes.accountRoute
import com.hosppy.routes.mailRoute
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        accountRoute()
        mailRoute()
    }
}
