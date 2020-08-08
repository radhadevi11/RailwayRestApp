package com.radha.railwayrest.app.service;

import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.app.mapper.TrainMapper;
import com.radha.railwayrest.db.repo.TrainRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TrainService {
    private TrainStopService trainStopService;
    private TrainRepo trainRepo;
    private TrainMapper trainMapper;

    public TrainService(TrainStopService trainStopService, TrainRepo trainRepo, TrainMapper trainMapper) {
        this.trainStopService = trainStopService;
        this.trainRepo = trainRepo;
        this.trainMapper = trainMapper;
    }


    public List<Train> getTrains(String sourceStationCode, String destinationStationCode) throws NoSuchFromStationException, NoSuchToStationException {
        //TODO Validate the sourceStationCode and destinationStationCode

        List<TrainStop> sourceStationTrainStops = trainStopService.getAllTrainStopsForStation(sourceStationCode);

        if(sourceStationTrainStops.isEmpty()){
            throw new NoSuchFromStationException("The given fromStation code " + sourceStationCode + " is not found");
        }

        List<TrainStop> destinationStationTrainStops = trainStopService.getAllTrainStopsForStation(destinationStationCode);

        if(destinationStationTrainStops.isEmpty()) {
            throw new NoSuchToStationException("The given toStation code " + destinationStationCode + " is not found");
        }
        Map<Integer, TrainStop> sourceStationMap = sourceStationTrainStops.stream()
                .collect(Collectors.toMap(trainStop -> trainStop.getTrain().getId(), Function.identity()));

        return getTrains(destinationStationTrainStops, sourceStationMap);


    }

     List<Train> getTrains(List<TrainStop> destinationStationTrainStops, Map<Integer, TrainStop> sourceStationMap) {

         return destinationStationTrainStops.stream()
                .filter(destinationStationTrainStop -> isBeforeStop(sourceStationMap, destinationStationTrainStop))
                .map(trainStop -> getFullTrain(trainStop))
                .collect(Collectors.toList());
    }
    Train getFullTrain(TrainStop trainStop) throws NoSuchTrainException {

        return trainRepo.findById(trainStop.getTrain().getId())
                .map(trainEntity -> trainMapper.toDomain(trainEntity))
                .orElseThrow(() -> new NoSuchTrainException(trainStop.getTrain().getId()));
    }

     boolean isBeforeStop(Map<Integer, TrainStop> sourceStationMap, TrainStop destinationStationTrainStop) {
        TrainStop sourceStationTrainStop = sourceStationMap.get(destinationStationTrainStop.getTrain().getId());
        return sourceStationTrainStop != null && sourceStationTrainStop.isBeforeStop(destinationStationTrainStop);
    }
}


