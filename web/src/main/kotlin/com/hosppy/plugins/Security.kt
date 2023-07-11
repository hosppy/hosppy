package com.hosppy.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import kotlin.collections.set

fun Application.configureSecurity() {

    data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
}
