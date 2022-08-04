package club.hosppy.common.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {

    private int code;

    private String status;

    private String message;
}
