package com.radha.railwayrest.rest.controller;

import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.rest.mapper.StationModelMapper;
import com.radha.railwayrest.rest.model.StationModel;
import com.radha.railwayrest.app.service.NoSuchFromStationException;
import com.radha.railwayrest.app.service.StationService;
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
    private StationModelMapper stationModelMapper;

    public StationRestController(StationService stationService, StationModelMapper stationModelMapper) {
        this.stationService = stationService;
        this.stationModelMapper = stationModelMapper;
    }

    @GetMapping
    public List<StationModel> getAllFromStations() throws IOException {
        return stationModelMapper.convertToStationModels(stationService.getFromStations());
    }

    @GetMapping("/{fromStationCode}")
    public List<StationModel> getAllToStations(@PathVariable(value = "fromStationCode")
                                                           String fromStationCode) throws IOException, NoSuchFromStationException {
        Map<String, Station> toStations =stationService.getToStations(fromStationCode);
        return stationModelMapper.convertToStationModels(toStations.values());
    }
}
