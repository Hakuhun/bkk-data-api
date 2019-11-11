package hu.oe.bakonyi.bkk.bkkdataapi.service;

import hu.oe.bakonyi.bkk.bkkdataapi.client.BkkClient;
import hu.oe.bakonyi.bkk.bkkdataapi.client.WeatherClient;
import hu.oe.bakonyi.bkk.bkkdataapi.converter.CoordToLocationConverter;
import hu.oe.bakonyi.bkk.bkkdataapi.converter.WeatherModel200ToBasicWeatherModelConverter;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkBusinessDataV2;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkData;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Model200;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Rain;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Snow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
public class BkkBusinessDataService {

    @Autowired
    BkkClient bkkClient;

    @Autowired
    WeatherClient weatherClient;

    @Autowired
    WeatherModel200ToBasicWeatherModelConverter weatherConverter;

    @Autowired
    CoordToLocationConverter coordConverter;


    public List<BkkBusinessDataV2> getData(String routeId) {
        List<BkkData> routeDatas = bkkClient.getRouteById(routeId);
        List<BkkBusinessDataV2> businessDatas = new ArrayList<>();

        for (BkkData route : routeDatas){
            Instant lastUpdate = Instant.ofEpochSecond(route.getLastUpdateTime());
            Model200 weather = weatherClient.getWeatherByCoord(coordConverter.toCoord(route.getLocation()));

            if(weather.getRain() == null){
                weather.setRain(new Rain(){{
                    set_3h(0.0);
                }});
            }

            if(weather.getSnow() == null){
                weather.setSnow(new Snow(){{
                    set_3h(0.0);
                }});
            }

            BkkBusinessDataV2 v2 = new BkkBusinessDataV2(){{
               setRouteId(route.getRouteId());
               setTripId(route.getTripId());
               setStopId(route.getStopId());
               setLastUpdateTime(lastUpdate.getEpochSecond());
               setDayOfWeek(lastUpdate.atZone(ZoneId.of("Europe/Budapest")).get(ChronoField.DAY_OF_WEEK));
               setMonth(lastUpdate.atZone(ZoneId.of("Europe/Budapest")).get(ChronoField.MONTH_OF_YEAR));
               setHour(getActualHour(route.getStopSequence(), route.getDepartureTime( ), route.getArrivalTime(), lastUpdate));
               setArrivalDiff(route.getArrivalDiff());
               setDepartureDiff(route.getDepartureDiff());
               setHumidity(weather.getMain().getHumidity());
               setPressure(weather.getMain().getPressure());
               setTemperature(weather.getMain().getTemp());
               setRain(weather.getRain().get_3h());
               setSnow(weather.getSnow().get_3h());
               setVehicleModel(route.getModel());
               setAlert((byte) (route.isAlert() ? 1 : 0));
               setVisibility(weather.getVisibility());
               setValue(0);
            }};
            businessDatas.add(v2);
        }
        return businessDatas;
    }

    private int getActualHour(int stopSequence, long departure, long arrival, Instant lastUpdate){
        int hour = 0;

        LocalDateTime time = LocalDateTime.ofEpochSecond(departure,0,  ZonedDateTime.ofInstant(Instant.ofEpochSecond(departure), ZoneId.of("CET")).getOffset());

        LocalDateTime midnight = LocalDateTime.of(time.toLocalDate(), LocalTime.MIDNIGHT);

        if((stopSequence == 0)){
            hour = Instant.ofEpochSecond(departure).atZone(ZoneId.of("Europe/Budapest")).get(ChronoField.HOUR_OF_DAY);
        }
        else{
            hour = Instant.ofEpochSecond(arrival).atZone(ZoneId.of("Europe/Budapest")).get(ChronoField.HOUR_OF_DAY);
        }

        if(hour == 0 && time.isBefore(ChronoLocalDateTime.from(midnight))){
            hour = lastUpdate.atZone(ZoneId.of("Europe/Budapest")).get(ChronoField.HOUR_OF_DAY);
        }
        return hour;
    }

}
