package club.hosppy.edison.core.interceptor;

import club.hosppy.edison.config.MappingProperties;
import club.hosppy.edison.core.http.RequestData;

public interface PreForwardRequestInterceptor {

    void intercept(RequestData data, MappingProperties mapping);
}
