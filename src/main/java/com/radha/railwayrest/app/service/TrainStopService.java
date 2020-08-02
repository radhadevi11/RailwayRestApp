package com.radha.railwayrest.app.service;

import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.app.mapper.TrainStopMapper;
import com.radha.railwayrest.db.entity.TrainStopEntity;
import com.radha.railwayrest.db.repo.TrainStopRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainStopService {
    private TrainStopRepo trainStopRepo;
    private TrainStopMapper trainStopMapper;

    public TrainStopService(TrainStopRepo trainStopRepo, TrainStopMapper trainStopMapper) {
        this.trainStopRepo = trainStopRepo;
        this.trainStopMapper = trainStopMapper;
    }
    List<TrainStop> getAllTrainStopsForStation (String stationCode) {

        List<TrainStopEntity> trainStops = trainStopRepo.findByStationCode (stationCode);

        return trainStops.stream()
                .map(trainStopEntity -> trainStopMapper.toDomain(trainStopEntity))
                .collect(Collectors.toList());
    }

}
