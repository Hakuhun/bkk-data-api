package hu.oe.bakonyi.bkk.bkkdataapi.service;

import hu.oe.bakonyi.bkk.bkkdataapi.client.BkkClient;
import hu.oe.bakonyi.bkk.bkkdataapi.client.WeatherClient;
import hu.oe.bakonyi.bkk.bkkdataapi.converter.BkkBusinessDataV2Converter;
import hu.oe.bakonyi.bkk.bkkdataapi.converter.CoordToLocationConverter;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkBusinessDataV2;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkData;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Model200;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
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
    CoordToLocationConverter coordConverter;

    @Autowired
    BkkBusinessDataV2Converter routeConverter;


    public List<BkkBusinessDataV2> getData(String routeId) {
        List<BkkData> routeDatas = bkkClient.getRouteById(routeId);
        List<BkkBusinessDataV2> businessDatas = new ArrayList<>();

        for (BkkData route : routeDatas){
            Instant lastUpdate = Instant.ofEpochSecond(route.getLastUpdateTime());
            Model200 weather = weatherClient.getWeatherByCoord(coordConverter.toCoord(route.getLocation()));
            businessDatas.add(routeConverter.convert(route, lastUpdate, weather));
        }
        return businessDatas;
    }



}
