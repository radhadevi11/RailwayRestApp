class AppController {
    constructor(appView) {
        this.appView = appView;
     this.mapViewController = new MapViewController(
         new MapView((station) => this.onStationClick(station)),
         mapViewModel);
     this.trainsViewController = new this.trainsViewController(
         new TrainsView(
             (train) => this.mapViewController.onTrainMouseOver(train),
          () => this.mapViewController.onTrainMouseOut()),
         trainsViewModel
     )
     
    }

    onStationClick(station) {
        this.trainView.onStationClicked(station);
    }

     getAllStations(){
        var request = new XMLHttpRequest(); //creating a object with the type of XMLHTTPRequest()
         request.open('GET','http://localhost:8080/stations',false); //open method with the 3 parameter
         request.send(null);//request body is null
         var stationsJsonArray=JSON.parse(request.responseText);//resposeText is a string
         //loop through this json array,
         //and convert each json objects to a station object add a station object to a station array
         var stations=[] ;
         stationsJsonArray.map(stationJson => stations.push(new Station(
                                                           stationJson.code,
                                                           stationJson.name,
                                                           stationJson.latitude,
                                                           stationJson.longitude)));
         return stations;

    }
}