package hu.oe.bakonyi.bkk.bkkdataapi.model;

import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.BasicWeatherModel;
import lombok.Data;

@Data
public class BkkBusinessData {
    private Location location;
    private BkkData bkk;
    private BasicWeatherModel weather;
    private long currentTime;
    private int month;
    private int dayOfTheWeek;
    private double value;
}
