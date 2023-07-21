package com.hosppy.models

data class MailTmpl(val name: String)

data class MailRequest(
    val to: String,
    val subject: String,
    val name: String,
    val tmpl: MailTmpl,
    val params: Map<String, Any> = mapOf()
)

data class MailProperty(
    val from: String,
    val fromName: String,
)