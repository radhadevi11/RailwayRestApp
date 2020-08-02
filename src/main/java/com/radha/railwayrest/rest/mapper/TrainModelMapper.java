package com.radha.railwayrest.rest.mapper;

import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.rest.model.StationModel;
import com.radha.railwayrest.rest.model.TrainModel;
import com.radha.railwayrest.rest.model.TrainStopModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class TrainModelMapper {
    private StationModelMapper stationModelMapper;
    private TrainStopModelMapper trainStopModelMapper;

    public TrainModelMapper(StationModelMapper stationModelMapper, TrainStopModelMapper trainStopModelMapper) {
        this.stationModelMapper = stationModelMapper;
        this.trainStopModelMapper = trainStopModelMapper;
    }

    public TrainModel convertToTrainModel(Train train) {
        Station sourceStation = train.getSourceStation();
        StationModel sourceStationModel = stationModelMapper.convertToStationModel(sourceStation);
        Station destinationStation = train.getDestinationStation();
        StationModel destinationStationModel = stationModelMapper.convertToStationModel(destinationStation);
        List<TrainStop> trainStops = train.getTrainStops();
        List<TrainStopModel> trainStopModels = trainStopModelMapper.convertToTrainStopModels(trainStops);
        return new TrainModel(train.getName(),train.getNumber(),sourceStationModel,destinationStationModel,trainStopModels);
    }
}
