package club.hosppy.edison.core.http;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

@Getter
public class UnmodifiableRequestData {

    protected HttpMethod method;

    protected String uri;

    protected String host;

    protected HttpHeaders headers;

    protected byte[] body;

    protected HttpServletRequest originRequest;

    public UnmodifiableRequestData(HttpMethod method, String host, String uri, HttpHeaders headers, byte[] body,
                                   HttpServletRequest originRequest) {
        this.method = method;
        this.uri = uri;
        this.host = host;
        this.headers = headers;
        this.body = body;
        this.originRequest = originRequest;
    }
}
