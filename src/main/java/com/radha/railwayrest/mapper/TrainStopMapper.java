package com.radha.railwayrest.mapper;

import com.radha.railwayrest.Station;
import com.radha.railwayrest.TrainStop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainStopMapper {

    private StationMapper stationMapper;

    public TrainStopMapper(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
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
        StationModel stationModel = stationMapper.convertToStationModel(station);
        return new TrainStopModel(trainStop.getArrivalTime(),trainStop.getDepartureTime(),trainStop.getSequence(),stationModel,trainStop.getDistance());


    }
}
