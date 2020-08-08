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

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceTest {
    public static final String SOURCE_STATION_CODE = "MAS";
    public static final String DESTINATION_STATION_CODE = "ED";
    private static final Integer CHENNAI_TRAIN_ID = 123;
    private static final Integer ERODE_TRAIN_ID = 2545;
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
    public void getTrainsWithInvalidSourceStation() throws NoSuchFromStationException, NoSuchToStationException {


        doReturn(Collections.emptyList()).when(trainStopService)
                .getAllTrainStopsForStation(SOURCE_STATION_CODE);

        Assertions.assertThatThrownBy(() -> trainService.getTrains(SOURCE_STATION_CODE, DESTINATION_STATION_CODE))
                .isInstanceOf(NoSuchFromStationException.class)
                .hasMessage("The given fromStation code " + SOURCE_STATION_CODE + " is not found");




    }

    @Test
    public void testIsBeforeStop() throws NoSuchFromStationException, NoSuchToStationException {

        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        Train train = mock(Train.class);
        Map<Integer, TrainStop> sourceStationMap = Collections.singletonMap(ERODE_TRAIN_ID, chenna123TrainStop);
        doReturn(train).when(erode123TrainStop).getTrain();
        doReturn(ERODE_TRAIN_ID).when(train).getId();
        doReturn(true).when(chenna123TrainStop).isBeforeStop(erode123TrainStop);

        boolean actual = trainService.isBeforeStop(sourceStationMap, erode123TrainStop);

        Assertions.assertThat(actual).isTrue();


    }

    @Test
    public void testIsBeforeStopSourceStationStopIsNull() throws NoSuchFromStationException, NoSuchToStationException {

        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        Train train = mock(Train.class);
        Map<Integer, TrainStop> sourceStationMap = mock(Map.class);
        doReturn(chenna123TrainStop).when(sourceStationMap).get(erode123TrainStop);
        doReturn(train).when(erode123TrainStop).getTrain();
        doReturn(ERODE_TRAIN_ID).when(train).getId();
        doReturn(null).when(sourceStationMap).get(ERODE_TRAIN_ID);
        doReturn(true).when(chenna123TrainStop).isBeforeStop(erode123TrainStop);

        boolean actual = trainService.isBeforeStop(sourceStationMap, erode123TrainStop);

        Assertions.assertThat(actual).isFalse();


    }

    @Test
    public void testNotBeforeStop() throws NoSuchFromStationException, NoSuchToStationException {

        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        Train train = mock(Train.class);
        Map<Integer, TrainStop> sourceStationMap = mock(Map.class);
        doReturn(chenna123TrainStop).when(sourceStationMap).get(erode123TrainStop);
        doReturn(train).when(erode123TrainStop).getTrain();
        doReturn(ERODE_TRAIN_ID).when(train).getId();
        doReturn(chenna123TrainStop).when(sourceStationMap).get(ERODE_TRAIN_ID);
        doReturn(false).when(chenna123TrainStop).isBeforeStop(erode123TrainStop);

        boolean actual = trainService.isBeforeStop(sourceStationMap, erode123TrainStop);

        Assertions.assertThat(actual).isFalse();


    }

    @Test
    public void testGetTrains() throws NoSuchFromStationException, NoSuchToStationException {
        Train train = mock(Train.class);
        List<TrainStop> destinationStationTrainStops = mock(List.class);
        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        TrainStop erode223TrainStop = mock(TrainStop.class);
        TrainStop covai423TrainStop = mock(TrainStop.class);
        Stream<TrainStop> destinationTrainStopStream = Stream.of(chenna123TrainStop, erode123TrainStop,
                erode223TrainStop, covai423TrainStop);
        doReturn(destinationTrainStopStream).when(destinationStationTrainStops).stream();
        Stream<TrainStop> destinationTrainStopStreamAfterFilter = Stream.of(chenna123TrainStop, erode123TrainStop,
                erode223TrainStop);
        /*doReturn(destinationTrainStopStreamAfterFilter).when(destinationTrainStopStream).filter();
        doReturn();
        Map<Integer, TrainStop> sourceStationMap = mock(Map.class);
        doReturn(chenna123TrainStop).when(sourceStationMap).get(ERODE_TRAIN_ID);
        doReturn();*/

        //List<Train> actual = trainService.getTrains(destinationStationTrainStops, sourceStationMap);



    }


}