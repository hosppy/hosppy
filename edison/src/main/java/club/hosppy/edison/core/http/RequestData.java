package club.hosppy.edison.core.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
public class RequestData extends UnmodifiableRequestData {

    private boolean needRedirect;

    private String redirectUrl;

    public RequestData(HttpMethod method, String host, String uri, HttpHeaders headers, byte[] body, HttpServletRequest request) {
        super(method, host, uri, headers, body, request);
    }
}
