package com.radha.railwayrest.rest.mapper;

import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.rest.model.StationModel;
import com.radha.railwayrest.rest.model.TrainStopModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainStopModelMapper {

    private StationModelMapper stationModelMapper;

    public TrainStopModelMapper(StationModelMapper stationModelMapper) {
        this.stationModelMapper = stationModelMapper;
    }

     List<TrainStopModel> convertToTrainStopModels(List<TrainStop> trainStops) {
        ArrayList<TrainStopModel> trainStopModels = new ArrayList<>();
        for(TrainStop trainStop :trainStops) {
            TrainStopModel trainStopModel = convertToTrainStopModel(trainStop);
            trainStopModels.add(trainStopModel);
        }
        return trainStopModels;


    }

    TrainStopModel convertToTrainStopModel(TrainStop trainStop) {
        Station station = trainStop.getStation();
        StationModel stationModel = stationModelMapper.convertToStationModel(station);
        return new TrainStopModel(trainStop.getArrivalTime(),trainStop.getDepartureTime(),trainStop.getSequence(),stationModel,trainStop.getDistance());


    }
}
