package club.hosppy.edison.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Edison configuration properties
 */
@Getter
@Setter
@ConfigurationProperties("edison")
public class EdisonProperties {
    /**
     * Faraday servlet filter order.
     */
    private int filterOrder = HIGHEST_PRECEDENCE + 100;
    /**
     * Enable programmatic mapping or not,
     * false only in dev environment, in dev we use mapping via configuration file
     */
    private boolean enableProgrammaticMapping = true;
    /**
     * Properties responsible for collecting metrics during HTTP requests forwarding.
     */
    @NestedConfigurationProperty
    private MetricsProperties metrics = new MetricsProperties();
    /**
     * Properties responsible for tracing HTTP requests proxying processes.
     */
    @NestedConfigurationProperty
    private TracingProperties tracing = new TracingProperties();
    /**
     * List of proxy mappings.
     */
    @NestedConfigurationProperty
    private List<MappingProperties> mappings = new ArrayList<>();
}
