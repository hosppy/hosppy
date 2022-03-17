package club.hosppy.edison.core.mapping;

import club.hosppy.edison.config.MappingProperties;
import club.hosppy.edison.exceptions.EdisonException;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.springframework.util.CollectionUtils.isEmpty;

public class  MappingValidator {

    public void validate(List<MappingProperties> mappings) {
        if (!isEmpty(mappings)) {
            mappings.forEach(this::correctMapping);
            int numberOfNames = mappings.stream()
                    .map(MappingProperties::getName)
                    .collect(toSet())
                    .size();
            if (numberOfNames < mappings.size()) {
                throw new EdisonException("Duplicated route names in mappings");
            }
            int numberOfHosts = mappings.stream()
                    .map(MappingProperties::getHost)
                    .collect(toSet())
                    .size();
            if (numberOfHosts < mappings.size()) {
                throw new EdisonException("Duplicated source hosts in mappings");
            }
            mappings.sort((mapping1, mapping2) -> mapping2.getHost().compareTo(mapping1.getHost()));
        }
    }

    protected void correctMapping(MappingProperties mapping) {
        validateName(mapping);
        validateDestinations(mapping);
        validateHost(mapping);
        validateTimeout(mapping);
    }

    protected void validateName(MappingProperties mapping) {
        if (isBlank(mapping.getName())) {
            throw new EdisonException("Empty name for mapping " + mapping);
        }
    }

    protected void validateDestinations(MappingProperties mapping) {
        if (isEmpty(mapping.getDestinations())) {
            throw new EdisonException("No destination hosts for mapping" + mapping);
        }
        List<String> correctedHosts = new ArrayList<>(mapping.getDestinations().size());
        mapping.getDestinations().forEach(destination -> {
            if (isBlank(destination)) {
                throw new EdisonException("Empty destination for mapping " + mapping);
            }
            if (!destination.matches(".+://.+")) {
                destination = "http://" + destination;
            }
            destination = removeEnd(destination, "/");
            correctedHosts.add(destination);
        });
        mapping.setDestinations(correctedHosts);
    }

    protected void validateHost(MappingProperties mapping) {
        if (isBlank(mapping.getHost())) {
            throw new EdisonException("No source host for mapping " + mapping);
        }
    }

    protected void validateTimeout(MappingProperties mapping) {
        int connectTimeout = mapping.getTimeout().getConnect();
        if (connectTimeout < 0) {
            throw new EdisonException("Invalid connect timeout value: " + connectTimeout);
        }
        int readTimeout = mapping.getTimeout().getRead();
        if (readTimeout < 0) {
            throw new EdisonException("Invalid read timeout value: " + readTimeout);
        }
    }
}
