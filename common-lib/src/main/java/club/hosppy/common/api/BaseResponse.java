package club.hosppy.common.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {

    private ResultCode code;

    private String message;
}
