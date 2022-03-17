package club.hosppy.common.services;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ServiceDirectory {

    private static Map<String, Service> serviceMap;

    static {

        Map<String, Service> map = new TreeMap<>();

        Service service = Service.builder()
                .security(SecurityConstant.SEC_AUTHENTICATED)
                .restrictDev(false)
                .backendDomain("account-service")
                .build();
        map.put("account", service);

        service = Service.builder()
                // Debug site for faraday proxy
                .security(SecurityConstant.SEC_PUBLIC)
                .restrictDev(true)
                .backendDomain("httpbin.org")
                .build();
        map.put("edison", service);

        serviceMap = Collections.unmodifiableMap(map);
    }

    public static Map<String, Service> getMapping() {
        return serviceMap;
    }
}
