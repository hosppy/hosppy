package club.hosppy.edison.core.mapping;

import club.hosppy.edison.config.EdisonProperties;
import club.hosppy.edison.config.MappingProperties;
import club.hosppy.edison.core.http.HttpClientProvider;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
public abstract class MappingProvider {

    private static final ILogger logger = SLoggerFactory.getLogger(MappingProvider.class);

    protected final EdisonProperties edisonProperties;

    protected final MappingValidator mappingValidator;

    protected final HttpClientProvider httpClientProvider;

    protected List<MappingProperties> mappings;

    public MappingProperties resolveMapping(String originHost, HttpServletRequest request) {
        if (shouldUpdateMappings(request)) {
            updateMappings();
        }
        return mappings.stream()
                .filter(mapping -> originHost.equalsIgnoreCase(mapping.getHost()))
                .findFirst()
                .orElse(null);
    }

    @PostConstruct
    protected synchronized void updateMappings() {
        List<MappingProperties> newMappings = retrieveMappings();
        mappingValidator.validate(newMappings);
        mappings = newMappings;
        httpClientProvider.updateHttpClients(mappings);
        logger.info("Destination mappings updated", mappings);
    }

    protected abstract boolean shouldUpdateMappings(HttpServletRequest request);

    protected abstract List<MappingProperties> retrieveMappings();
}
