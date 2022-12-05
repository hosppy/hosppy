package club.hosppy.email.service

import club.hosppy.email.config.MailConfig
import club.hosppy.email.dto.EmailRequest
import com.aliyun.dm20151123.Client
import com.aliyun.dm20151123.models.SingleSendMailRequest
import com.aliyun.teautil.models.RuntimeOptions
import com.github.structlog4j.IToLog
import com.github.structlog4j.SLoggerFactory
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateException
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.StringWriter

@Service
class EmailService(
    private val client: Client,
    private val mailConfig: MailConfig,
    private val config: Configuration,
    private val tmplCache: MutableMap<String, Template?> = HashMap(8)
) {

    @Async(MailConfig.ASYNC_EXECUTOR_NAME)
    fun sendAsync(request: EmailRequest) {
        val htmlBody: String
        try {
            val tmplName = request.tmpl.name
            var tmpl = tmplCache[tmplName]
            if (tmpl == null) {
                tmpl = config.getTemplate("$tmplName.ftl")
                tmplCache[tmplName] = tmpl
            }
            val writer = StringWriter()
            tmpl!!.process(request.params, writer)
            htmlBody = writer.toString()
        } catch (e: IOException) {
            throw IllegalStateException(e)
        } catch (e: TemplateException) {
            throw IllegalStateException(e)
        }
        val logContext = IToLog {
            arrayOf<Any>(
                "subject", request.subject,
                "to", request.to,
                "html_body", htmlBody,
                "name", request.name
            )
        }
        val mailRequest = SingleSendMailRequest()
        mailRequest.setAccountName(mailConfig.from)
        mailRequest.setFromAlias(mailConfig.fromName)
        mailRequest.setAddressType(1)
        mailRequest.setToAddress(request.to)
        mailRequest.setReplyToAddress(false)
        mailRequest.setSubject(request.subject)
        mailRequest.setHtmlBody(htmlBody)
        val options = RuntimeOptions()
        try {
            val response = client.singleSendMailWithOptions(mailRequest, options)
            logger.info("Successfully send email - request id : " + response.body.requestId, logContext)
        } catch (e: Exception) {
            logger.error("Failed send email", e)
        }
    }

    companion object {
        private val logger = SLoggerFactory.getLogger(EmailService::class.java)
    }
}