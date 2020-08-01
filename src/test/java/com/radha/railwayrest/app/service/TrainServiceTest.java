package com.radha.railwayrest.app.service;


import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import org.assertj.core.api.Assertions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceTest {
    public static final String SOURCE_STATION_CODE = "MAS";
    public static final String DESTINATION_STATION_CODE = "ED";
    private static final Integer TRAIN_ID = 123;
    @Mock
    private TrainStopService trainStopService;

    @InjectMocks
    private TrainService trainService;

    @Test
    public void testGetTrainsBetweenSourceAndDestinationStation() {

        Train expected = mock(Train.class);
        doReturn(TRAIN_ID).when(expected).getId();
        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        doReturn(expected).when(chenna123TrainStop).getTrain();
        doReturn(true).when(chenna123TrainStop).isBeforeStop(erode123TrainStop);
        TrainStop coimbatore123TrainStop = mock(TrainStop.class);
        doReturn(expected).when(erode123TrainStop).getTrain();
        List<TrainStop> sourceStationTrainStops = Arrays.asList(chenna123TrainStop);
        List<TrainStop> destinationStationStops = Arrays.asList(erode123TrainStop);
        doReturn(sourceStationTrainStops).when(trainStopService).getAllTrainStopsForStation(SOURCE_STATION_CODE);
        doReturn(destinationStationStops).when(trainStopService).getAllTrainStopsForStation(DESTINATION_STATION_CODE);

        List<Train> actual = trainService.getTrains(SOURCE_STATION_CODE, DESTINATION_STATION_CODE);

        Assertions.assertThat(actual).containsExactly(expected);


    }
}