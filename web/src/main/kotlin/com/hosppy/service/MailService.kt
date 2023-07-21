package com.hosppy.service

import com.aliyun.dm20151123.Client
import com.aliyun.dm20151123.models.SingleSendMailRequest
import com.aliyun.teautil.models.RuntimeOptions
import com.github.structlog4j.IToLog
import com.hosppy.models.MailProperty
import com.hosppy.models.MailRequest
import freemarker.template.Configuration
import org.slf4j.LoggerFactory
import java.io.StringWriter

internal val log = LoggerFactory.getLogger(MailService::class.java)

class MailService(
    private val client: Client,
    private val mailProperty: MailProperty,
    private val templateConfiguration: Configuration,
) {

    suspend fun send(request: MailRequest) {
        val fileName = request.tmpl.name
        val tmpl = templateConfiguration.getTemplate(request.tmpl.name)
        val writer = StringWriter()
        tmpl.process(request.params, writer)
        val htmlBody = writer.toString()
        val logContext = IToLog {
            arrayOf<Any>(
                "subject", request.subject,
                "to", request.to,
                "html_body", htmlBody,
                "name", request.name
            )
        }
        val mailRequest = SingleSendMailRequest()
        mailRequest.setAccountName(mailProperty.from)
        mailRequest.setFromAlias(mailProperty.fromName)
        mailRequest.setAddressType(1)
        mailRequest.setToAddress(request.to)
        mailRequest.setReplyToAddress(false)
        mailRequest.setSubject(request.subject)
        mailRequest.setHtmlBody(htmlBody)
        val options = RuntimeOptions()
        try {
            val response = client.singleSendMailWithOptions(mailRequest, options)
            log.info("Successfully send email - request id : " + response.body.requestId, logContext)
        } catch (e: Exception) {
            log.error("Failed send email", e)
        }
    }
}