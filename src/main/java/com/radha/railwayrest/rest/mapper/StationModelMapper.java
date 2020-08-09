package com.radha.railwayrest.rest.mapper;


import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.rest.model.StationModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class StationModelMapper {
    public StationModel convertToStationModel(Station station) {
        return new StationModel(station.getName(),station.getCode(), station.getLatitude(), station.getLongitude());
    }
     public List<StationModel> convertToStationModels(Collection<Station> stations) {
        List<StationModel> stationModels = new ArrayList<>();
        for(Station station : stations){
            stationModels.add(convertToStationModel(station));

        }
        return stationModels;
    }
}
