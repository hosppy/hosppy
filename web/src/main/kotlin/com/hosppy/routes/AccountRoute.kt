package com.hosppy.routes

import com.hosppy.service.AccountService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.accountRoute() {
    val accountService: AccountService by inject()

    route("/accounts") {
        get {
            val accounts = accountService.list()
            call.respond(accounts)
        }
        get("/{email}") {
            val email = call.parameters["email"].orEmpty()
            call.respond(accountService.getByEmail(email))
        }
    }
}