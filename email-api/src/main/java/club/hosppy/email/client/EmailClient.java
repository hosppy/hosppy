package club.hosppy.email.client;

import club.hosppy.email.EmailConstant;
import club.hosppy.email.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = EmailConstant.SERVICE_NAME, path = "/email", url = "${hosppy.email-service-endpoint}")
public interface EmailClient {

    @PostMapping("/send")
    void send(@RequestBody @Valid EmailRequest request);
}
