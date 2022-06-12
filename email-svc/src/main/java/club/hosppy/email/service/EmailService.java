package club.hosppy.email.service;

import club.hosppy.email.config.AppConfig;
import club.hosppy.email.config.MailConfig;
import club.hosppy.email.dto.EmailRequest;
import com.aliyun.dm20151123.Client;
import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.dm20151123.models.SingleSendMailResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.IToLog;
import com.github.structlog4j.SLoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private static final ILogger logger = SLoggerFactory.getLogger(EmailService.class);

    private final Client client;

    private final MailConfig mailConfig;

    @Async(AppConfig.ASYNC_EXECUTOR_NAME)
    public void sendEmailAsync(EmailRequest request) {
        IToLog logContext = () -> new Object[]{
                "subject", request.getSubject(),
                "to", request.getTo(),
                "html_body", request.getHtmlBody(),
                "name", request.getName()
        };

        SingleSendMailRequest mailRequest = new SingleSendMailRequest();
        mailRequest.setAccountName(mailConfig.getFrom());
        mailRequest.setFromAlias(mailConfig.getFromName());
        mailRequest.setAddressType(1);
        mailRequest.setToAddress(request.getTo());
        mailRequest.setReplyToAddress(false);
        mailRequest.setSubject(request.getSubject());
        mailRequest.setHtmlBody(request.getHtmlBody());

        RuntimeOptions options = new RuntimeOptions();
        try {
            SingleSendMailResponse response = client.singleSendMailWithOptions(mailRequest, options);
            logger.info("Successfully send email - request id : " + response.body.requestId, logContext);
        } catch (Exception e) {
            logger.error("Failed send email", e);
        }
    }
}
