package club.hosppy.edison.core.trace;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForwardRequest extends IncomingRequest {

    protected String mappingName;

    protected byte[] body;
}
