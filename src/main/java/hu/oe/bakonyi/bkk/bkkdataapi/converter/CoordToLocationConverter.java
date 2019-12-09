package hu.oe.bakonyi.bkk.bkkdataapi.converter;

import hu.oe.bakonyi.bkk.bkkdataapi.model.Location;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Coord;
import org.springframework.stereotype.Service;

@Service
public class CoordToLocationConverter {
    public Location toLocation(Coord source) {
        return Location.builder().lon(source.getLon()).lat(source.getLat()).build();
    }

    public Coord toCoord(Location location) {
        return Coord.builder().lat(location.getLat()).lon(location.getLon()).build();
    }
}
