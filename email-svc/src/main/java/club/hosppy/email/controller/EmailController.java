package club.hosppy.email.controller;

import club.hosppy.email.dto.EmailRequest;
import club.hosppy.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public void send(@RequestBody @Valid EmailRequest request) {
        emailService.sendEmailAsync(request);
    }
}
