package club.hosppy.email.service;

import club.hosppy.email.config.AppConfig;
import club.hosppy.email.config.MailConfig;
import club.hosppy.email.dto.EmailRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
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

    private final IAcsClient acsClient;

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

        try {
            SingleSendMailResponse response = acsClient.getAcsResponse(mailRequest);
            logger.info("Successfully send email - request id : " + response.getRequestId(), logContext);
        } catch (ClientException e) {
            logger.error("Failed send email", e);
        }
    }
}
