package club.hosppy.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {

    @NotBlank
    private String id;

    @NotBlank
    private String hospitalId;

    @NotBlank
    private String name;

    @NotBlank
    private String intro;

    @NotBlank
    private String parentId;
}
