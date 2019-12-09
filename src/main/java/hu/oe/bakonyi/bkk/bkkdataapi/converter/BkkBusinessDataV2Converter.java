package hu.oe.bakonyi.bkk.bkkdataapi.converter;

import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkBusinessDataV2;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkData;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Model200;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoField;

@Service
public class BkkBusinessDataV2Converter {

    public BkkBusinessDataV2 convert(BkkData route, Instant lastUpdate, Model200 weather) throws ConversionException {
        if(route == null || route.getRouteId() == null || route.getStopId()== null ||route.getTripId() == null){
            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(BkkData.class),
                    TypeDescriptor.valueOf(BkkBusinessDataV2.class),
                    route,
                    new Exception("Hiba a konverzió közben, hiányzó útadatok"));
        }

        if(weather == null || weather.getMain().getTemp().isNaN() ||  weather.getRain() == null ||weather.getSnow() == null || weather.getVisibility() == null){
            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(Model200.class),
                    TypeDescriptor.valueOf(BkkBusinessDataV2.class),
                    route,
                    new Exception("Hiba a konverzió közben, hiányzó időjárásadatok"));
        }

        if(lastUpdate == null){
            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(Instant.class),
                    TypeDescriptor.valueOf(BkkBusinessDataV2.class),
                    route,
                    new Exception("Hiba a konverzió közben, hiányzó időadatok"));
        }

        return new BkkBusinessDataV2(){{
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
    }

    int getActualHour(int stopSequence, long departure, long arrival, Instant lastUpdate){
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
