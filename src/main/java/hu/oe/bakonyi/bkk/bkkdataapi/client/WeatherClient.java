package hu.oe.bakonyi.bkk.bkkdataapi.client;

import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Coord;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Model200;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "weather",
        url = "${api.weatherUrl}"
)
public interface WeatherClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/weather"
    )
    Model200 getWeatherByCoord(@RequestBody Coord coord);
}
