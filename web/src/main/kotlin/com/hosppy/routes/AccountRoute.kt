package com.hosppy.routes

import com.hosppy.models.CreateAccountRequest
import com.hosppy.service.AccountService
import io.ktor.server.application.*
import io.ktor.server.request.*
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
        post {
            val request = call.receive(CreateAccountRequest::class)
            val accountDto = accountService.create(request.name, request.email, request.password)
            call.respond(accountDto)
        }
    }
}