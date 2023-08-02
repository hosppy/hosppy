package com.hosppy.models

import com.hosppy.common.annotations.AllOpen
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.modelmapper.PropertyMap

object Accounts : IntIdTable("account") {
    val name: Column<String> = varchar("name", 255)
    val passwordHash: Column<String> = varchar("password_hash", 255)
    val email: Column<String> = varchar("email", 255).uniqueIndex()
    val phoneNumber: Column<String?> = varchar("phone_number", 255).uniqueIndex().nullable()
    val memberSince: Column<Instant> = timestamp("member_since").default(Clock.System.now())
    val confirmedAndActive: Column<Boolean> = bool("confirmed_and_active").default(false)
    val avatarUrl: Column<String?> = varchar("avatar_url", 255).default("").nullable()
}

@AllOpen
class Account(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Account>(Accounts)

    var name by Accounts.name
    var passwordHash by Accounts.passwordHash
    var email by Accounts.email
    var phoneNumber by Accounts.phoneNumber
    var memberSince by Accounts.memberSince
    var confirmedAndActive by Accounts.confirmedAndActive
    var avatarUrl by Accounts.avatarUrl
}

@AllOpen
@Serializable
class AccountDto {
    var id: Int? = null
    var name: String? = null
    var email: String = ""
    var confirmedAndActive = false
    var memberSince: Instant? = null
    var phoneNumber: String? = null
    var avatarUrl: String? = null
}

object AccountMapper : PropertyMap<Account, AccountDto>() {
    override fun configure() {
    }
}

@Serializable
data class CreateAccountRequest(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
)