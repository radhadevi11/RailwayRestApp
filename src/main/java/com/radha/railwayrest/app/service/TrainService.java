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


    public List<Train> getTrains(String sourceStationCode, String destinationStationCode) {
        //TODO Validate the sourceStationCode and destinationStationCode
        List<TrainStop> sourceStationTrainStops = trainStopService.getAllTrainStopsForStation(sourceStationCode);
        List<TrainStop> destinationStationTrainStops = trainStopService.getAllTrainStopsForStation(destinationStationCode);

        Map<Integer, TrainStop> sourceStationMap = sourceStationTrainStops.stream()
                .collect(Collectors.toMap(trainStop -> trainStop.getTrain().getId(), Function.identity()));

        return destinationStationTrainStops.stream()
                .filter(destinationStationTrainStop -> {
                    TrainStop sourceStationTrainStop = sourceStationMap.get(destinationStationTrainStop.getTrain().getId());
                    return sourceStationTrainStop != null && sourceStationTrainStop.isBeforeStop(destinationStationTrainStop);
                })
                .map(TrainStop::getTrain)
                .collect(Collectors.toList());


    }
}


