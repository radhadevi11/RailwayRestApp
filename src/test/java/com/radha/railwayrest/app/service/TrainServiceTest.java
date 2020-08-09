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

import static org.assertj.core.api.Assertions.*;
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

        assertThat(actual).containsExactly(expected);


    }



    @Test
    public void getTrainsWithInvalidSourceStation() throws NoSuchFromStationException, NoSuchToStationException {


        doReturn(Collections.emptyList()).when(trainStopService)
                .getAllTrainStopsForStation(SOURCE_STATION_CODE);

        assertThatThrownBy(() -> trainService.getTrains(SOURCE_STATION_CODE, DESTINATION_STATION_CODE))
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

        assertThat(actual).isTrue();


    }

    @Test
    public void testIsBeforeStopSourceStationStopIsNull() throws NoSuchFromStationException, NoSuchToStationException {

        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        Train train = mock(Train.class);
        Map<Integer, TrainStop> sourceStationMap = mock(Map.class);
        doReturn(train).when(erode123TrainStop).getTrain();
        doReturn(ERODE_TRAIN_ID).when(train).getId();
        doReturn(null).when(sourceStationMap).get(ERODE_TRAIN_ID);
        doReturn(true).when(chenna123TrainStop).isBeforeStop(erode123TrainStop);

        boolean actual = trainService.isBeforeStop(sourceStationMap, erode123TrainStop);

        assertThat(actual).isFalse();


    }

    @Test
    public void testNotBeforeStop() throws NoSuchFromStationException, NoSuchToStationException {

        TrainStop chennai123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        Train train = mock(Train.class);
        Map<Integer, TrainStop> sourceStationMap = mock(Map.class);
        doReturn(train).when(erode123TrainStop).getTrain();
        doReturn(ERODE_TRAIN_ID).when(train).getId();
        doReturn(chennai123TrainStop).when(sourceStationMap).get(ERODE_TRAIN_ID);
        doReturn(false).when(chennai123TrainStop).isBeforeStop(erode123TrainStop);

        boolean actual = trainService.isBeforeStop(sourceStationMap, erode123TrainStop);

        assertThat(actual).isFalse();


    }

    @Test
    public void testGetTrains() throws NoSuchFromStationException, NoSuchToStationException {
        Train train123 = mock(Train.class);
        Train train223 = mock(Train.class);
        TrainStop chennai123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        TrainStop erode223TrainStop = mock(TrainStop.class);
        TrainStop covai423TrainStop = mock(TrainStop.class);
        List<TrainStop> destinationStationTrainStops = Arrays.asList(chennai123TrainStop, erode123TrainStop,
                erode223TrainStop, covai423TrainStop);
        Map<Integer, TrainStop> sourceStationMap = new HashMap<>();
        doReturn(true).when(trainService).isBeforeStop(sourceStationMap, chennai123TrainStop);
        doReturn(false).when(trainService).isBeforeStop(sourceStationMap, erode123TrainStop);
        doReturn(true).when(trainService).isBeforeStop(sourceStationMap, erode223TrainStop);
        doReturn(false).when(trainService).isBeforeStop(sourceStationMap, covai423TrainStop);
        doReturn(train123).when(trainService).getFullTrain(chennai123TrainStop);
        doReturn(train223).when(trainService).getFullTrain(erode223TrainStop);

        List<Train> actual = trainService.getTrains(destinationStationTrainStops, sourceStationMap);

        assertThat(actual).containsExactly(train123, train223);


    }

    @Test
    public void testGetTrainsMain() throws NoSuchFromStationException, NoSuchToStationException {
        TrainStop chenna123TrainStop = mock(TrainStop.class);
        TrainStop erode123TrainStop = mock(TrainStop.class);
        Train expected = mock(Train.class);
        doReturn(CHENNAI_TRAIN_ID).when(expected).getId();
        doReturn(expected).when(chenna123TrainStop).getTrain();
        doReturn(expected).when(erode123TrainStop).getTrain();
        List<TrainStop> sourceStationTrainStops = Arrays.asList(chenna123TrainStop);
        List<TrainStop> destinationStationStops = Arrays.asList(erode123TrainStop);
        doReturn(sourceStationTrainStops).when(trainStopService).getAllTrainStopsForStation(SOURCE_STATION_CODE);
        doReturn(destinationStationStops).when(trainStopService).getAllTrainStopsForStation(DESTINATION_STATION_CODE);
        Map<Integer, TrainStop> sourceStationMap = Collections.singletonMap(CHENNAI_TRAIN_ID, chenna123TrainStop);
        List<Train> expectedList = Arrays.asList(expected);
        doReturn(expectedList).when(trainService).getTrains(destinationStationStops, sourceStationMap);

        List<Train> actual = trainService.getTrains(SOURCE_STATION_CODE, DESTINATION_STATION_CODE);

        assertThat(actual).containsExactly(expected);


    }




}