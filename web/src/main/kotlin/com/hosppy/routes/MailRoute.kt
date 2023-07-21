package com.hosppy.routes

import com.hosppy.models.MailRequest
import com.hosppy.service.MailService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import org.koin.ktor.ext.inject

internal val log = KtorSimpleLogger("com.hosppy.routes.MailRoute")

fun Routing.mailRoute() {
    val mailService: MailService by inject()

    route("/email") {
        post("/send") {
            val mailRequest = call.receive(MailRequest::class)
            mailService.send(mailRequest)
        }
    }
}