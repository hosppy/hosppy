package club.hosppy.edison.core.trace;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class IncomingRequest extends HttpEntity {

    protected HttpMethod method;

    protected String uri;

    protected String host;
}
