package com.radha.railwayrest.controller;

import com.radha.railwayrest.Station;
import com.radha.railwayrest.mapper.StationMapper;
import com.radha.railwayrest.mapper.StationModel;
import com.radha.railwayrest.service.NoSuchFromStationException;
import com.radha.railwayrest.service.StationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stations")
public class StationRestController {
    private StationService stationService;
    private StationMapper stationMapper;

    public StationRestController(StationService stationService, StationMapper stationMapper) {
        this.stationService = stationService;
        this.stationMapper = stationMapper;
    }

    @GetMapping
    public List<StationModel> getAllFromStations() throws IOException {
        //StationDaoImpl stationDao = new StationDaoImpl();
       // return stationDao.getAll();
        Map<String, Station> fromStations = stationService.getFromStations();
        return stationMapper.convertToStationModels(fromStations.values());
    }

    @GetMapping("/{fromStationCode}")
    public List<StationModel> getAllToStations(@PathVariable(value = "fromStationCode")
                                                           String fromStationCode) throws IOException, NoSuchFromStationException {
        Map<String, Station> toStations =stationService.getToStations(fromStationCode);
        return stationMapper.convertToStationModels(toStations.values());
    }
}
