package com.radha.railwayrest.app.service;

import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.app.domain.TimeTable;
import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.db.repo.TrainStopRepo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TrainService {
    private TrainStopService trainStopService;

    public TrainService(TrainStopService trainStopService) {
        this.trainStopService = trainStopService;
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

    private List<Train> getTrains(List<TrainStop> destinationStationTrainStops, Map<Integer, TrainStop> sourceStationMap) {
        return destinationStationTrainStops.stream()
                .filter(destinationStationTrainStop -> isBeforeStop(sourceStationMap, destinationStationTrainStop))
                .map(TrainStop::getTrain)
                .collect(Collectors.toList());
    }

    private boolean isBeforeStop(Map<Integer, TrainStop> sourceStationMap, TrainStop destinationStationTrainStop) {
        TrainStop sourceStationTrainStop = sourceStationMap.get(destinationStationTrainStop.getTrain().getId());
        return sourceStationTrainStop != null && sourceStationTrainStop.isBeforeStop(destinationStationTrainStop);
    }
}


