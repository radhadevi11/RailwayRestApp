package com.radha.railwayrest.db.repo;


import com.radha.railwayrest.db.entity.StationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StationRepo extends CrudRepository<StationEntity, Integer> {
    StationEntity findById(int id);
    List<StationEntity> findAll();
    StationEntity findByCode(String stationCode);
}
