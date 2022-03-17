package club.hosppy.edison.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MappingProperties {

    private String name;

    private String host = "";

    private List<String> destinations = new ArrayList<>();

    private TimeoutProperties timeout = new TimeoutProperties();

    private Map<String, Object> customConfiguration = new HashMap<>();

    public MappingProperties copy() {
        MappingProperties clone = new MappingProperties();
        clone.setName(name);
        clone.setHost(host);
        clone.setDestinations(destinations == null ? null : new ArrayList<>(destinations));
        clone.setTimeout(timeout);
        clone.setCustomConfiguration(customConfiguration == null ? null : new HashMap<>(customConfiguration));
        return clone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("name", name)
                .append("host", host)
                .append("destinations", destinations)
                .append("timeout", timeout)
                .append("customConfiguration", customConfiguration)
                .toString();
    }

    @Getter
    @Setter
    public static class TimeoutProperties {

        private int connect = 2000;

        private int read = 20000;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                    .append("connect", connect)
                    .append("read", read)
                    .toString();
        }
    }
}
