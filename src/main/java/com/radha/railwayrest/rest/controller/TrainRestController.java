package com.radha.railwayrest.rest.controller;

import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.rest.mapper.TrainMapper;
import com.radha.railwayrest.rest.mapper.TrainModel;
import com.radha.railwayrest.app.service.NoSuchFromStationException;
import com.radha.railwayrest.app.service.NoSuchToStationException;
import com.radha.railwayrest.app.service.TrainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trains")

public class TrainRestController {
    private TrainService trainService;
    private TrainMapper trainMapper;
    public TrainRestController(TrainService trainService, TrainMapper trainMapper) {
        this.trainService = trainService;
        this.trainMapper = trainMapper;
    }
    @GetMapping("/{sourceStationCode}/{destinationStationCode}")
    List<TrainModel> getTrains(@PathVariable( "sourceStationCode") String sourceStationCode,
                               @PathVariable("destinationStationCode") String destinationStationCode) throws NoSuchToStationException, IOException, NoSuchFromStationException, NoSuchToStationException, NoSuchFromStationException {
        List<Train> trains =  trainService.getTrains(sourceStationCode, destinationStationCode);
        ArrayList<TrainModel> trainModels = new ArrayList<>();
        for(Train train : trains){
            trainModels.add(trainMapper.convertToTrainModel(train));

        }
        return trainModels;
    }


}
