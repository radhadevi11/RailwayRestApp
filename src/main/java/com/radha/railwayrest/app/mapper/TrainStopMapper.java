package com.radha.railwayrest.app.mapper;

import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.db.entity.TrainStopEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainStopMapper {
    private StationMapper stationMapper;
    private TrainMapper trainMapper;

    public TrainStopMapper(StationMapper stationMapper, TrainMapper trainMapper) {
        this.stationMapper = stationMapper;
        this.trainMapper = trainMapper;
    }

    public TrainStop toDomain(TrainStopEntity trainStopEntity) {
        return new TrainStop(trainStopEntity.getId(),
                trainStopEntity.getArrivalTime(),
                trainStopEntity.getDepartureTime(),
                trainMapper.toDomain(trainStopEntity.getTrain()),
                trainStopEntity.getSequence(),
                stationMapper.toDomain(trainStopEntity.getStation()),
                0);
    }
}
