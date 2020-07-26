package com.radha.railwayrest.db.repo;


import com.radha.railwayrest.db.entity.TrainStopEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TrainStopRepo extends CrudRepository<TrainStopEntity, Integer> {
    @Override
    Optional<TrainStopEntity> findById(Integer integer);
    List<TrainStopEntity> findByTrainId(Integer trainId);
    List<TrainStopEntity> findByStationId(Integer stationId);
    List<TrainStopEntity> findByStationCode(String stationCode);
}
