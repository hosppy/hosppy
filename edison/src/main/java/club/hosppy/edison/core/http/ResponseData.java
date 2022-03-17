package club.hosppy.edison.core.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseData {

    protected HttpStatus status;

    protected HttpHeaders headers;

    protected byte[] body;

    protected UnmodifiableRequestData requestData;

    public ResponseData(HttpStatus status, HttpHeaders headers, byte[] body, UnmodifiableRequestData requestData) {
        this.status = status;
        this.headers = new HttpHeaders();
        this.headers.putAll(headers);
        this.body = body;
        this.requestData = requestData;
    }
}
