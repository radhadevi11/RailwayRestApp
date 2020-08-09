package com.radha.railwayrest.app.mapper;

import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.db.entity.StationEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StationMapper {
    public Station toDomain(StationEntity stationEntity) {
        return new Station(stationEntity.getId(),
                stationEntity.getName(),
                stationEntity.getCode(),
                stationEntity.getLatitude(),
                stationEntity.getLongitude());
    }
    public List<Station> toDomain(List<StationEntity> stationEntities) {
        return stationEntities.stream()
                .map(stationEntity -> toDomain(stationEntity))
                .collect(Collectors.toList());
    }
}
