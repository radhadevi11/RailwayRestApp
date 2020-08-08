package com.radha.railwayrest.db.repo;

import com.radha.railwayrest.db.entity.StationEntity;
import com.radha.railwayrest.db.entity.TrainEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TrainRepo extends CrudRepository<TrainEntity, Integer> {

    TrainEntity findById(int id);
    List<TrainEntity> findBySourceStation(StationEntity sourceStation);
    List<TrainEntity> findBySourceStationId(Integer sourceStationId);

}
