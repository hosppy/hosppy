package com.hosppy.plugins

import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun Application.configureDatabases(config: ApplicationConfig) {

    val database = Database.connect(
        driver = config.property("ktor.db.driver").getString(),
        url = config.property("ktor.db.url").getString(),
        user = config.property("ktor.db.username").getString(),
        password = config.property("ktor.db.password").getString(),
    )
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }