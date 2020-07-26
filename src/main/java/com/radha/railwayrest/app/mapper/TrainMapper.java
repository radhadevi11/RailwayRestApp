package com.radha.railwayrest.app.mapper;

import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.db.entity.TrainEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainMapper {
    private StationMapper stationMapper;

    public TrainMapper(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    public Train toDomain(TrainEntity trainEntity) {
        return new Train(trainEntity.getId(),
                trainEntity.getName(),
                trainEntity.getNumber(),
                stationMapper.toDomain(trainEntity.getSourceStation()),
                stationMapper.toDomain(trainEntity.getDestinationStation()));
    }
}
