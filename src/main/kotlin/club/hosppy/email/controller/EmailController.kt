package club.hosppy.email.controller

import club.hosppy.email.dto.EmailRequest
import club.hosppy.email.service.EmailService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequiredArgsConstructor
@RestController
@RequestMapping("/email")
class EmailController(
    private val emailService: EmailService
) {
    @PostMapping("/send")
    fun send(@RequestBody request: @Valid EmailRequest) {
        emailService.sendAsync(request)
    }
}