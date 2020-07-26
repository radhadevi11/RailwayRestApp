package com.radha.railwayrest.rest.mapper;


import com.radha.railwayrest.app.domain.Station;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class StationMapper {
    public StationModel convertToStationModel(Station station) {
        return new StationModel(station.getName(),station.getCode(),station.getLatLng());
    }
     public ArrayList<StationModel> convertToStationModels(Collection<Station> stations) {
        ArrayList<StationModel> stationModels = new ArrayList<>();
        for(Station station : stations){
            stationModels.add(convertToStationModel(station));

        }
        return stationModels;
    }
}
