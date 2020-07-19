package com.radha.railwayrest.mapper;

import com.radha.railwayrest.Station;
import com.radha.railwayrest.Train;
import com.radha.railwayrest.TrainStop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class TrainMapper {
    private StationMapper stationMapper;
    private TrainStopMapper trainStopMapper;

    public TrainMapper(StationMapper stationMapper, TrainStopMapper trainStopMapper) {
        this.stationMapper = stationMapper;
        this.trainStopMapper = trainStopMapper;
    }

    public TrainModel convertToTrainModel(Train train) {
        Station sourceStation = train.getSourceStation();
        StationModel sourceStationModel = stationMapper.convertToStationModel(sourceStation);
        Station destinationStation = train.getDestinationStation();
        StationModel destinationStationModel = stationMapper.convertToStationModel(destinationStation);
        ArrayList<TrainStop> trainStops = train.getTrainStops();
        List<TrainStopModel> trainStopModels = trainStopMapper.convertToTrainStopModels(trainStops);
        return new TrainModel(train.getName(),train.getNumber(),sourceStationModel,destinationStationModel,trainStopModels);
    }
}
