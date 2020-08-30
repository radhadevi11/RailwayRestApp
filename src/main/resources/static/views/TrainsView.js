class TrainsView {

constructor {
    this.trainsController = new TrainsController();
    document.getElementById("getTrains").addEventListener('click', getPossibleTrains);
}

onStationClicked(station) {
    if(document.getElementById('sourceStation').value==""){
         document.getElementById('sourceStation').value = station.name;
         document.getElementById('sourceStationCode').value = station.code;
    }
      else if(document.getElementById('destinationStation').value==""){
        document.getElementById('destinationStation').value = station.name;
          document.getElementById('destinationStationCode').value = station.code;
      }
}
function checkEmptyTextBox(sourceStationValue, destinationStationValue) {
          if(sourceStationValue == "" || destinationStationValue==""){
            alert("Please choose both the source and the destination stations");
            return false;
          }
          else{
            return true;
          }

}

 render() {

 }

  getPossibleTrains(){
      var sourceStationCodeValue = document.getElementById('sourceStationCode').value;//getting the element value by it's ID
      var destinationStationCodeValue= document.getElementById('destinationStationCode').value;
      if(!checkEmptyTextBox(sourceStationCodeValue, destinationStationCodeValue)){
        return;
      }
      const trains = this.trainsController.getAllTrains();
      if(trains.length==0){
        alert("There is no train available for this stations");
        return;
      }
      document.getElementById("trainResult").setAttribute("class", "visible");
      const table = document.getElementById("trainsTable");
      const tBody = table.tBodies.item(0);
      tBody.innerHTML = "";
      trains.forEach((train) => populateRowWithTrainDetails(train, tBody));
    }
     populateRowWithTrainDetails(train, tBody) {
        const row = tBody.insertRow();
        row.insertCell(0).innerHTML = train.name;
        row.insertCell(1).innerHTML = train.number;
        row.insertCell(2).innerHTML = train.sourceStation.name;
        row.insertCell(3).innerHTML = train.destinationStation.name;
        row.addEventListener("mouseover", function() {drawLines(train);});
        row.addEventListener("mouseout", function(){removeLine()});
        row.addEventListener("click", function(){showStops(train)});
     }

      convertToTrain(trainJson) {
         return new Train(trainJson.number,
                        trainJson.name,
                        trainJson.sourceStation,
                        trainJson.destinationStation,
                        trainJson.trainStops.map(convertToTrainStop));


     }

}