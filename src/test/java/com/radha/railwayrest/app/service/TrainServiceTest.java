package com.radha.railwayrest.app.service;


import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import org.assertj.core.api.Assertions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceTest {
    public static final String SOURCE_STATION_CODE = "MAS";
    public static final String DESTINATION_STATION_CODE = "ED";
    private static final Integer CHENNAI_TRAIN_ID = 123;
    private static final Integer COIM_TRAIN_ID = 2545;
    @Mock
    private TrainStopService trainStopService;

    @InjectMocks
    @Spy
    private TrainService trainService;

    @Test
    public void testGetTrainsBetweenSourceAndDestinationStation() throws NoSuchFromStationException, NoSuchToStationException {

        Train expected = mock(Train.class);
        doReturn(CHENNAI_TRAIN_ID).when(expected).getId();
        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        doReturn(expected).when(chenna123TrainStop).getTrain();
        doReturn(true).when(chenna123TrainStop).isBeforeStop(erode123TrainStop);
        doReturn(expected).when(erode123TrainStop).getTrain();
        List<TrainStop> sourceStationTrainStops = Arrays.asList(chenna123TrainStop);
        List<TrainStop> destinationStationStops = Arrays.asList(erode123TrainStop);
        doReturn(sourceStationTrainStops).when(trainStopService).getAllTrainStopsForStation(SOURCE_STATION_CODE);
        doReturn(destinationStationStops).when(trainStopService).getAllTrainStopsForStation(DESTINATION_STATION_CODE);

        List<Train> actual = trainService.getTrains(SOURCE_STATION_CODE, DESTINATION_STATION_CODE);

        Assertions.assertThat(actual).containsExactly(expected);


    }

    @Test
    public void testGetMultipleTrains() throws NoSuchFromStationException, NoSuchToStationException {

        Train train1 = mock(Train.class);
        Train train2 = mock(Train.class);
        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        TrainStop coimbatore223TrainStop = mock(TrainStop.class);
        TrainStop salem223TrainStop = mock(TrainStop.class);
        doReturn(CHENNAI_TRAIN_ID).when(train1).getId();
        doReturn(COIM_TRAIN_ID).when(train2).getId();
        doReturn(train1).when(chenna123TrainStop).getTrain();
        doReturn(true).when(chenna123TrainStop).isBeforeStop(erode123TrainStop);
        doReturn(train1).when(erode123TrainStop).getTrain();
        doReturn(true).when(coimbatore223TrainStop).isBeforeStop(erode123TrainStop);
        doReturn(train2).when(coimbatore223TrainStop).getTrain();
        doReturn(train2).when(salem223TrainStop).getTrain();
        List<TrainStop> sourceStationTrainStops = Arrays.asList(chenna123TrainStop, coimbatore223TrainStop);
        List<TrainStop> destinationStationStops = Arrays.asList(erode123TrainStop, salem223TrainStop);
        doReturn(sourceStationTrainStops).when(trainStopService).getAllTrainStopsForStation(SOURCE_STATION_CODE);
        doReturn(destinationStationStops).when(trainStopService).getAllTrainStopsForStation(DESTINATION_STATION_CODE);

        List<Train> actual = trainService.getTrains(SOURCE_STATION_CODE, DESTINATION_STATION_CODE);

        Assertions.assertThat(actual).containsExactly(train1, train2);


    }

    @Test
    public void getTrainsWithInvalidSourceStation() throws NoSuchFromStationException, NoSuchToStationException {


        doReturn(Collections.emptyList()).when(trainStopService)
                .getAllTrainStopsForStation(SOURCE_STATION_CODE);

        Assertions.assertThatThrownBy(() -> trainService.getTrains(SOURCE_STATION_CODE, DESTINATION_STATION_CODE))
                .isInstanceOf(NoSuchFromStationException.class)
                .hasMessage("The given fromStation code " + SOURCE_STATION_CODE + " is not found");




    }

}