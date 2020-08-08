package com.radha.railwayrest.app.mapper;

import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.db.entity.TrainStopEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainStopMapper {
    private StationMapper stationMapper;

    public TrainStopMapper(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    public TrainStop toDomain(TrainStopEntity trainStopEntity) {
        return new TrainStop(trainStopEntity.getId(),
                trainStopEntity.getArrivalTime(),
                trainStopEntity.getDepartureTime(),
                new Train(trainStopEntity.getTrain().getId()),
                trainStopEntity.getSequence(),
                stationMapper.toDomain(trainStopEntity.getStation()),

                0);
    }
    public List<TrainStop> toDomain(List<TrainStopEntity> trainStopEntities) {
        return trainStopEntities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
