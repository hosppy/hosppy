package club.hosppy.edison.core.mapping;

import club.hosppy.edison.config.EdisonProperties;
import club.hosppy.edison.config.MappingProperties;
import club.hosppy.edison.core.http.HttpClientProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationMappingProvider extends MappingProvider {

    public ConfigurationMappingProvider(
            EdisonProperties edisonProperties,
            MappingValidator mappingsValidator,
            HttpClientProvider httpClientProvider
    ) {
        super(edisonProperties, mappingsValidator, httpClientProvider);
    }

    @Override
    protected boolean shouldUpdateMappings(HttpServletRequest request) {
        return false;
    }

    @Override
    protected List<MappingProperties> retrieveMappings() {
        return edisonProperties.getMappings().stream()
                .map(MappingProperties::copy)
                .collect(Collectors.toList());
    }
}
