package hu.oe.bakonyi.bkk.bkkdataapi.converter;

import hu.oe.bakonyi.bkk.bkkdataapi.model.Location;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.BasicWeatherModel;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Model200;
import org.springframework.stereotype.Service;

@Service
public class WeatherModel200ToBasicWeatherModelConverter{

    public BasicWeatherModel convert(Model200 source) {
        BasicWeatherModel wModel = new BasicWeatherModel();

        if(source == null){
            return null;
        }

        if(source.getMain().getTemp() != null){
            wModel.setTemperature(source.getMain().getTemp());
        }else{
            wModel.setTemperature(0);
        }
        if(source.getMain().getHumidity() != null){
            wModel.setHumidity(source.getMain().getHumidity());
        }
        if(source.getMain().getPressure() != null){
            wModel.setPressure(source.getMain().getPressure());
        }
        if(source.getRain()!= null && source.getRain().get_3h() !=null){
            wModel.setRainIntensity(source.getRain().get_3h());
        }else{
            wModel.setRainIntensity(0);
        }
        if(source.getSnow() != null){
            wModel.setSnowIntensity(source.getSnow().get_3h());
        }else{
            wModel.setSnowIntensity(0);
        }
        if(source.getVisibility() != null){
            wModel.setVisibility(source.getVisibility());
        }else{
            wModel.setVisibility(0);
        }
        if (source.getCoord() != null){
            wModel.setLocation(Location.builder().lat(source.getCoord().getLat()).lon(source.getCoord().getLon()).build());
        }

        return wModel;
    }
}
