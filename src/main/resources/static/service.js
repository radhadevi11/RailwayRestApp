
    var map;




     function arrayOfCities(){
          var cities = getAllStations();
           map = new google.maps.Map(document.getElementById('map'), {/*google.maps.map constructor The ID where the map will place,which location will be center,How much the map will be zoom*/
            center:{lat:11.5246,lng: 77.4702},
            zoom:7
          });
          cities.forEach(city => city.addMarker(map));
     }



    function showStops(train){
      //show the intermediate stations for the train which the user choose
      //Get the train from trainPosition of trains array
      //For each TrainStop in the train print the information in a table row
      //put the table in the stops div innerHtml

      /*show all the trainStops of the given train in the stops div
        - get all trainStops from the given trainPosition
        - table = get the table element
        - for each trainStops
            create a row for table
            create 5 cells in the row
      */

      const currentTrainStops=train.trainStops;
      const table = document.getElementById("trainStops");
      /*for(let i = 0; i < table.rows.length; i++) {
        table.deleteRow(0);
      }*/
      const tBody = table.tBodies.item(0);
      tBody.innerHTML = "";
      const populateRow = (trainStop) => {
                                         populateRowWithTrainStopDetails(tBody, trainStop);
                                         }
      currentTrainStops.forEach(populateRow);
      openStopsPopUp();


      /*var trainStopDetail ="";
      const reducer = (trainStopRowString, trainStop) => trainStopRowString + "<tr>\n"+
                                                            "<td>"+trainStop.arrivalTime+"</td>\n"+
                                                            "<td>"+trainStop.departureTime+"</td>\n"+
                                                            "<td>"+trainStop.sequence+"</td>\n"+
                                                            "<td>"+trainStop.stationName+"</td>\n"+
                                                            "<td>"+trainStop.distance+"</td>\n"+
                                                            "</tr>\n"
      document.getElementById('stops').innerHTML = tableHeader+currentTrainStops.reduce(reducer, "")+"</table>";*/

      //document.getElementById('stops').innerHTML =  document.getElementById('stops').innerHTML+""

    }

    function populateRowWithTrainStopDetails(tBody, trainStop) {
        const row = tBody.insertRow();
        row.insertCell(0).innerHTML = trainStop.arrivalTime;
        row.insertCell(1).innerHTML = trainStop.departureTime;
        row.insertCell(2).innerHTML = trainStop.sequence;
        row.insertCell(3).innerHTML = trainStop.stationName;
        row.insertCell(4).innerHTML = trainStop.distance;
    }
    function closeStopsPopUp(){
       document.getElementById('stops').setAttribute('class','hidden');
    }
    function openStopsPopUp(){
      document.getElementById('stops').setAttribute('class','visible');
    }

    function drawLines(train){
      var currentTrainStops=train.trainStops;
      const stationPathCoordinates = currentTrainStops.map(trainStop =>
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