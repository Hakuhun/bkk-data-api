package hu.oe.bakonyi.bkk.bkkdataapi.client;

import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkData;
import hu.oe.bakonyi.bkk.bkkdataapi.model.Routes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "routetrip",
        url = "${api.bkkUrl}"
)
public interface BkkClient {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/route"
    )
    List<BkkData> getRouteById(@RequestParam("route") String route);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/routes"
    )
    List<Routes> getStoredRoutes();
}
