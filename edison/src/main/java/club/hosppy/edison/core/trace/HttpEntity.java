package club.hosppy.edison.core.trace;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
public abstract class HttpEntity {

    protected HttpHeaders headers;
}
