package club.hosppy.edison.core.interceptor;

import club.hosppy.edison.config.MappingProperties;
import club.hosppy.edison.core.http.ResponseData;

public interface PostForwardResponseInterceptor {

    void intercept(ResponseData data, MappingProperties mapping);
}
