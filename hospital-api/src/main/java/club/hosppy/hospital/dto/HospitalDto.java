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
public class HospitalDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private int type;

    @NotBlank
    private String provinceCode;

    @NotBlank
    private String cityCode;

    @NotBlank
    private String districtCode;

    @NotBlank
    private String address;

    @NotBlank
    private String intro;

    private int status;
}
