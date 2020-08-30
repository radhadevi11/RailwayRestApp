class TrainsController {
    function getAllTrains(sourceStationCodeValue, destinationStationCodeValue){
      var request = new XMLHttpRequest();
      request.open('GET','http://localhost:8080/trains/'+sourceStationCodeValue+
                  '/'+destinationStationCodeValue,false);
      request.send(null);
      var trainsJsonArray=JSON.parse(request.responseText);//loop through this json array,
      //and convert each json objects to a station object add a station object to a station array
      return trainsJsonArray.map(convertToTrain);

    }
}