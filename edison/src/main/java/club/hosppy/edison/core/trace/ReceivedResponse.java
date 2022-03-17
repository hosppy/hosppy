package club.hosppy.edison.core.trace;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ReceivedResponse extends HttpEntity {

    protected HttpStatus status;

    protected byte[] body;
}
