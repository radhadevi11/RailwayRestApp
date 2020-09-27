import TrainsView from '/views/TrainsView.js';
import TrainsViewModel from '/viewmodel/TrainsViewModel.js';
import TrainsController from '/controller/TrainsController.js';
import MapView from '/views/MapView.js';
import MapViewModel from '/viewmodel/MapViewModel.js';
import MapViewController from '/controller/MapViewController.js';
import TrainStopsController from '/controller/TrainStopsController.js';
import TrainStopsView from '/views/TrainStopsView.js';
import TrainStopsViewModel from '/viewmodel/TrainStopsViewModel.js';


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
    console.log("Mapview's Map object:" + mapView.map);
    const mapViewModel = new MapViewModel(this.getAllStations());
    const mapViewController = new MapViewController(mapView, mapViewModel);
    const trainStopsView = new TrainStopsView();
    const trainStopsViewModel = new TrainStopsViewModel(null);
    const trainStopsController = new TrainStopsController(trainStopsView, trainStopsViewModel);
    trainsView.onTrainSelect((train) => trainStopsController.onTrainClick(train));
    console.log("mapViewController mapView map:" + mapViewController.mapView.map);

  }
  getAllStations() {
    var request = new XMLHttpRequest(); //creating a object with the type of XMLHTTPRequest()
    request.open('GET', 'http://localhost:8080/stations', false); //open method with the 3 parameter
    request.send(null);//request body is null
    var stationsJsonArray = JSON.parse(request.responseText);//resposeText is a string
    //loop through this json array,
    //and convert each json objects to a station object add a station object to a station array
    var stations = [];
    stationsJsonArray.map(stationJson => stations.push(new Station(
      stationJson.code,
      stationJson.name,
      stationJson.latitude,
      stationJson.longitude)));
    return stations;

  }
}
