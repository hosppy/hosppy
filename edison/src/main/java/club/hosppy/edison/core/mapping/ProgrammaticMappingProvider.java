package club.hosppy.edison.core.mapping;

import club.hosppy.common.env.EnvConfig;
import club.hosppy.common.services.Service;
import club.hosppy.common.services.ServiceDirectory;
import club.hosppy.edison.config.EdisonProperties;
import club.hosppy.edison.config.MappingProperties;
import club.hosppy.edison.core.http.HttpClientProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgrammaticMappingProvider extends MappingProvider {
    protected final EnvConfig envConfig;

    public ProgrammaticMappingProvider(
            EnvConfig envConfig,
            EdisonProperties edisonProperties,
            MappingValidator mappingValidator,
            HttpClientProvider httpClientProvider
    ) {
        super(edisonProperties, mappingValidator, httpClientProvider);
        this.envConfig = envConfig;
    }

    @Override
    protected boolean shouldUpdateMappings(HttpServletRequest request) {
        return false;
    }

    @Override
    protected List<MappingProperties> retrieveMappings() {
        List<MappingProperties> mappings = new ArrayList<>();
        Map<String, Service> serviceMap = ServiceDirectory.getMapping();
        for (String key : serviceMap.keySet()) {
            String subDomain = key.toLowerCase();
            Service service = serviceMap.get(key);
            MappingProperties mapping = new MappingProperties();
            mapping.setName(subDomain + "_route");
            mapping.setHost(subDomain + "." + envConfig.getExternalApex());
            // No security on backend right now :-(
            String dest = "http://" + service.getBackendDomain();
            mapping.setDestinations(List.of(dest));
            mappings.add(mapping);
        }
        return mappings;
    }
}
