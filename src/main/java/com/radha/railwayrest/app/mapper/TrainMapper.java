package com.radha.railwayrest.app.mapper;

import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.db.entity.TrainEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TrainMapper {
    private StationMapper stationMapper;
    private TrainStopMapper trainStopMapper;

    public TrainMapper(StationMapper stationMapper, TrainStopMapper trainStopMapper) {
        this.stationMapper = stationMapper;
        this.trainStopMapper = trainStopMapper;
    }

    public Train toDomain(TrainEntity trainEntity) {
        return new Train(trainEntity.getId(),
                trainEntity.getName(),
                trainEntity.getNumber(),
                stationMapper.toDomain(trainEntity.getSourceStation()),
                stationMapper.toDomain(trainEntity.getDestinationStation()),
                trainStopMapper.toDomain(trainEntity.getTrainStops()));

    }
}
