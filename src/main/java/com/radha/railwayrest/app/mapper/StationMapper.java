package com.radha.railwayrest.app.mapper;

import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.db.entity.StationEntity;
import org.springframework.stereotype.Component;

@Component
public class StationMapper {
    public Station toDomain(StationEntity stationEntity) {
        return new Station(stationEntity.getId(), stationEntity.getName(), stationEntity.getCode());
    }
}
