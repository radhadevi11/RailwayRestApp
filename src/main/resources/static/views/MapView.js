export default class MapView {

  constructor(map, onStationClick) {
    this.map = map;
    this.onStationClick = onStationClick;
    this.stationPath;
  }

  render(mapViewModel) {
    console.log("Map object "+ this.map);
    mapViewModel.stations.forEach(station => this.addMarker(station, this.map));
  }
  renderTrain(mapViewModel) {
    if(!mapViewModel.train) {
      if(!this.stationPath) {
        return;
      }
      this.stationPath.setMap(null);
    }
    var currentTrainStops = mapViewModel.train.trainStops;
    const stationPathCoordinates = currentTrainStops.map(trainStop =>
      ({
        lat: trainStop.latitude,
        lng: trainStop.longitude
      }));

    this.stationPath = new google.maps.Polyline({
      path: stationPathCoordinates,
      strokeColor: '#FF0000',
      strokeOpacity: 1.0,
      strokeWeight: 2
    });

    this.stationPath.setMap(this.map);

  }
  drawLines(train) {
    
  }
  removeLine() {
    
  }

  addMarker(station, map) {
    var circle = new google.maps.Circle({
      strokeColor: '#FF0000',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      fillColor: '#FF0000',
      fillOpacity: 0.35,
      map: map,
      center: { lat: station.latitude, lng: station.longitude },
      radius: 3000,
      title: station.name
    });
    var name = station.name;
    var code = station.code;
    circle.addListener('click', () => this.onStationClick(station));
    circle.addListener('mouseover', function () {
      document.getElementById('map').setAttribute('title', name);
    });
    circle.addListener('mouseout', function () {
      document.getElementById('map').removeAttribute('title');
    });

  }


}