package club.hosppy.edison.core.interceptor;

import club.hosppy.edison.config.MappingProperties;
import club.hosppy.edison.core.http.ResponseData;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public class CacheResponseInterceptor implements PostForwardResponseInterceptor {

    @Override
    public void intercept(ResponseData data, MappingProperties mapping) {
        HttpHeaders respHeaders = data.getHeaders();
        List<String> values = respHeaders.get(HttpHeaders.CONTENT_TYPE);
        if (!isEmpty(values) && values.contains("text/html")) {
            respHeaders.set(HttpHeaders.CACHE_CONTROL, "no-cache");
        }
    }
}
