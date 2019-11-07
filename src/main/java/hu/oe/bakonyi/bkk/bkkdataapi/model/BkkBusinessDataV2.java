package hu.oe.bakonyi.bkk.bkkdataapi.model;

import lombok.Data;

@Data
public class BkkBusinessDataV2 {

    public BkkBusinessDataV2() {
    }

    private double temperature, humidity, pressure, snow, rain, visibility;
    private double departureDiff, arrivalDiff, value = 0;
    private String routeId, stopId, vehicleModel, tripId;
    private int month, dayOfWeek, hour;
    private Long lastUpdateTime;
    private byte alert;

    public BkkBusinessDataV2(BkkBusinessData v1) {
        this.routeId = v1.getBkk().getRouteId();
        this.stopId = v1.getBkk().getStopId();
        this.vehicleModel = v1.getBkk().getModel();

        this.month = v1.getMonth();
        this.dayOfWeek = v1.getDayOfTheWeek();

        this.temperature = v1.getWeather().getTemperature();
        this.humidity = v1.getWeather().getHumidity();
        this.pressure = v1.getWeather().getPressure();
        this.snow = v1.getWeather().getSnowIntensity();
        this.rain = v1.getWeather().getRainIntensity();
        this.visibility = v1.getWeather().getVisibility();

        this.departureDiff = v1.getBkk().getDepartureDiff();
        this.arrivalDiff = v1.getBkk().getArrivalDiff();
        this.tripId = v1.getBkk().getTripId();
        this.lastUpdateTime = v1.getCurrentTime();
    }
}
