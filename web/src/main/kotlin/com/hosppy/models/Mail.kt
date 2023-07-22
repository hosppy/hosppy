package com.hosppy.models

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class MailTmpl(val name: String) {
    companion object {
        val ACTIVATE_ACCOUNT = MailTmpl("activate_account")
    }
}

@Serializable
data class MailRequest(
    val to: String,
    val subject: String,
    val name: String,
    val tmpl: MailTmpl,
    val params: Map<String, String>,
)

data class MailProperty(
    val from: String,
    val fromName: String,
)