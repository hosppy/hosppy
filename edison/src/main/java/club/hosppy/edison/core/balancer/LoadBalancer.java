package club.hosppy.edison.core.balancer;

import java.util.List;

public interface LoadBalancer {

    String chooseDestination(List<String> destnations);
}
