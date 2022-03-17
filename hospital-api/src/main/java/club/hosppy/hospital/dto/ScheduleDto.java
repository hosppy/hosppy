package club.hosppy.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {

    private Integer id;

    @NotBlank
    private String hospitalId;

    @NotBlank
    private String departmentId;

    @NotBlank
    private String title;

    @NotBlank
    private String doctorName;

    @NotBlank
    private String skill;

    private Date workDate;

    private int reservedNumber;

    private int availableNumber;

    private int amount;

    private int status;
}
