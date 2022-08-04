package club.hosppy.email.dto;

import club.hosppy.email.EmailTmpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    @NotBlank(message = "Please provide an email")
    private String to;

    @NotBlank(message = "Please provide a subject")
    private String subject;

    private String name;

    @NotNull
    private EmailTmpl tmpl;

    private Map<String, Object> params;
}
