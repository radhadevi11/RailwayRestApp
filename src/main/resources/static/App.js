import TrainsView from '/views/TrainsView.js';
import TrainsViewModel from '/viewmodel/TrainsViewModel.js';
import TrainsController from '/controller/TrainsController.js';
import MapView from '/views/MapView.js';
import MapViewModel from '/viewmodel/MapViewModel.js';
import MapViewController from '/controller/MapViewController.js';
import TrainStopsController from '/controller/TrainStopsController.js';
import TrainStopsView from '/views/TrainStopsView.js';
import TrainStopsViewModel from '/viewmodel/TrainStopsViewModel.js';
import Station from './station.js';

export default class App {
  constructor() {
    this.map = new google.maps.Map(document.getElementById('map'), {/*google.maps.map constructor The ID where the map will place,which location will be center,How much the map will be zoom*/
      center: { lat: 11.5246, lng: 77.4702 },
      zoom: 7
    });
    const trainsView = new TrainsView(this.map);
    const trainsViewModel = new TrainsViewModel(null, null, null, null);
    const trainsController = new TrainsController(trainsView, trainsViewModel);
    const mapView = new MapView(this.map, (station) => trainsController.onStationClick(station));
    this.getAllStations().then((stations) => {
      const mapViewModel = new MapViewModel(stations);
      const mapViewController = new MapViewController(mapView, mapViewModel);

    });
    const trainStopsView = new TrainStopsView();
    const trainStopsViewModel = new TrainStopsViewModel(null);
    const trainStopsController = new TrainStopsController(trainStopsView, trainStopsViewModel);
    trainsView.onTrainSelect((train) => trainStopsController.onTrainClick(train));

  }
  getAllStations() {

    return window.fetch('http://localhost:8080/stations')
      .then(response => response.json())
      .then(stationsJsonArray =>
        stationsJsonArray.map((stationJson) =>
          this.convertToStation(stationJson)));


  }
  convertToStations(stationsJsonArray) {
    var stations = [];
    stationsJsonArray.forEach(stationJson => stations.push(this.convertToStation(stationJson)));
    return stations;
  }
  convertToStation(stationJson) {
    return new Station(
      stationJson.code,
      stationJson.name,
      stationJson.latitude,
      stationJson.longitude);
  }
}
