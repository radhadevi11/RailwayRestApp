    var trains;
    var map;

    function getPossibleTrains(){
      if(!checkEmptyTextBox()){
        return;
      }
      trains=getAllTrains();
      if(trains.length==0){
        alert("There is no train available for this stations");
      }
      var tableHeader=
        "<table id="+"train"+">\n"+
        "<tr>\n"+
        "<th>TrainName</th>\n"+
        "<th>TrainNumber</th>\n"+
        "<th>SourceStation</th>\n"+
        "<th>DestinationStation</th>\n"+
        "</tr>\n";


        const reducer = (tableRowsString, train, i) => tableRowsString +"<tr onmouseover=\"drawLines("+i+")\"onmouseout=\"removeLine()\"onclick=\"showStops("+i+")\">\n"+
                                                                     "<td>"+train.name+"</td>\n"+
                                                                     "<td>"+train.number+"</td>\n"+
                                                                     "<td>"+train.sourceStation.name+"</td>\n"+
                                                                     "<td>"+train.destinationStation.name+"</td>\n"+
                                                                     "</tr>\n";

        /*trains.map(train => "<tr onmouseover=\"drawLines("+i+")\"onmouseout=\"removeLine()\"onclick=\"showStops("+i+")\">\n"+
                            "<td>"+train.name+"</td>\n"+
                            "<td>"+train.number+"</td>\n"+
                            "<td>"+train.sourceStation.name+"</td>\n"+
                            "<td>"+train.destinationStation.name+"</td>\n"+
                            "</tr>\n")
              .join("");

      var trainDetail ="";
      for(var i=0;i<trains.length;i++){
         var tableBody=
                "<tr onmouseover=\"drawLines("+i+")\"onmouseout=\"removeLine()\"onclick=\"showStops("+i+")\">\n"+
                "<td>"+trains[i].name+"</td>\n"+
                "<td>"+trains[i].number+"</td>\n"+
                "<td>"+trains[i].sourceStation.name+"</td>\n"+
                "<td>"+trains[i].destinationStation.name+"</td>\n"+
                "</tr>\n";
         trainDetail = trainDetail+tableBody;

      }*/

      document.getElementById('trainResult').innerHTML=tableHeader+trains.reduce(reducer, "")+"</table>";
    }


     function arrayOfCities(){
          var cities = getAllStations();
           map = new google.maps.Map(document.getElementById('map'), {/*google.maps.map constructor The ID where the map will place,which location will be center,How much the map will be zoom*/
            center:{lat:11.5246,lng: 77.4702},
            zoom:7
          });
          for(var i=0;i<cities.length;i++){
            cities[i].addMarker(map);//calling addMarker function for adding marker for given

          }
     }
     function checkEmptyTextBox() {
          var sourceStationValue = document.getElementById('sourceStation').value;
          var destinationStationValue = document.getElementById('destinationStation').value;
          if(sourceStationValue == "" || destinationStationValue==""){
            alert("Please choose both the source and the destination stations");
            return false;
          }
          else{
            return true;
          }

     }
    function showStops(trainPosition){
      //show the intermediate stations for the train which the user choose
      //Get the train from trainPosition of trains array
      //For each TrainStop in the train print the information in a table row
      //put the table in the stops div innerHtml
      var currentTrainStops=trains[trainPosition].trainStops;
      var tableHeader=
          "<table id="+"train"+">\n"+
      "<tr>\n"+
        "<th>ArrivalTime</th>\n"+
        "<th>DepartureTime</th>\n"+
        "<th>Sequence</th>\n"+
        "<th>stationName</th>\n"+
        "<th>Distance</th>\n"+
      "</tr>\n";

      var trainStopDetail ="";
          for(var i=0;i<currentTrainStops.length;i++){

            var tableBody=
                 "<tr>\n"+
            "<td>"+currentTrainStops[i].arrivalTime+"</td>\n"+
            "<td>"+currentTrainStops[i].departureTime+"</td>\n"+
            "<td>"+currentTrainStops[i].sequence+"</td>\n"+
            "<td>"+currentTrainStops[i].stationName+"</td>\n"+
            "<td>"+currentTrainStops[i].distance+"</td>\n"+
            "</tr>\n";
            trainStopDetail = trainStopDetail+tableBody;

          }
      document.getElementById('stops').innerHTML = tableHeader+trainStopDetail+"</table>";
      document.getElementById('stops').innerHTML =  document.getElementById('stops').innerHTML+"<button onclick=\"closeStopsPopUp()\">Close</button>";
      openStopsPopUp();
      document.getElementById('stops').innerHTML =  document.getElementById('stops').innerHTML+""

    }
    function closeStopsPopUp(){
       document.getElementById('stops').setAttribute('class','hiddenStops');
    }
    function openStopsPopUp(){
      document.getElementById('stops').setAttribute('class','visibleStops');
    }

    function drawLines(trainPosition){
      var currentTrainStops=trains[trainPosition].trainStops;
      var stationPathCoordinates =[];
      stationPathCoordinates = currentTrainStops.map(trainStop =>
                                                    ({lat: trainStop.latitude,
                                                    lng: trainStop.longitude
                                                    }));

     stationPath = new google.maps.Polyline({
          path: stationPathCoordinates,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 2
        });

      addLine();
    }

    function addLine() {
        stationPath.setMap(map);
    }
    function removeLine() {
        stationPath.setMap(null);
    }
    function getAllStations(){
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
    function getAllTrains(){
         var sourceStationCodeValue = document.getElementById('sourceStationCode').value;//getting the element value by it's ID
         var destinationStationCodeValue= document.getElementById('destinationStationCode').value;
         var request = new XMLHttpRequest();
         request.open('GET','http://localhost:8080/trains/'+sourceStationCodeValue+
                     '/'+destinationStationCodeValue,false);
         request.send(null);
         var trainsJsonArray=JSON.parse(request.responseText);//loop through this json array,
         //and convert each json objects to a station object add a station object to a station array
         return trainsJsonArray.map(convertToTrain);

    }
    function convertToTrain(trainJson) {
        return new Train(trainJson.number,
                    	trainJson.name,
                    	trainJson.sourceStation,
                    	trainJson.destinationStation,
                    	trainJson.trainStops.map(convertToTrainStop));


    }
    const convertToTrainStop = trainStopJson =>
        new TrainStop(trainStopJson.arrivalTime,
                     trainStopJson.departureTime,
                     trainStopJson.sequence,
                     trainStopJson.station.name,
                     trainStopJson.distance,
                     trainStopJson.station.latitude,
                     trainStopJson.station.longitude);

    function getToStations(){
        var sourceStationCodeValue = document.getElementById('sourceStationCode').value;
        var request = new XMLHttpRequest(); //creating a object with the type of XMLHTTPRequest()
         request.open('GET','http://localhost:8080/railway/tostations?sourceStationCode='+sourceStationCodeValue,false); //open method with the 3 parameter 
         request.send(null);//request body is null
         var toStationsJsonArray=JSON.parse(request.responseText);
         var toStations=[] ;
         toStations = toStationsJsonArray.map(createStation);
         return toStations;

    }
    const createStation = (stationJson) => {
        return new Station(
                  new LatLng(stationJson.latLng.latitude,
                  stationJson.latLng.longitude),
                  stationJson.code,
                  stationJson.name);
}
     function getDummyTrains(){
    //return the list of all trains
    var trains = [new Train("1234","Erode exp",new Station((new LatLng(11.3410,77.7172)),"ED","Erode"),
                  new Station((new LatLng(11.0168,76.9558)),"CBE","Coimbatore")),
                new Train("4567","Chennai Exp",new Station((new LatLng(13.0827,80.2707)),"MAS","Chennai"),
                 new Station((new LatLng(14.4426,79.98646)),"NLR","Nellore"))];
       return trains;

    }
    function getDummyStations() {
    //return the list of all stations
    var cities = [new Station((new LatLng(11.3410,77.7172)),"ED","Erode"),
                  new Station((new LatLng(11.0168,76.9558)),"CBE","Coimbatore"),
                 new Station((new LatLng(13.0827,80.2707)),"MAS","Chennai")];
       return cities;

    }