package club.hosppy.edison.core.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;

@Getter
@AllArgsConstructor
public class ForwardDestination {

    protected final URI uri;

    protected final String mappingName;

    protected final String mappingMetricsName;
}
