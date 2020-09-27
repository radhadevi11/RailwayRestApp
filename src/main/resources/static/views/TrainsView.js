export default class TrainsView {

  constructor(map) {
    this.map = map;
  }

  onGetTrainsClick(handler) {
    document.getElementById("getTrains").addEventListener('click', () => handler());
  }

  onResetClick(handler) {
    document.getElementById("reset").addEventListener('click', () => handler());
  }

  onTrainMouseOver(handler) {
    this.onTrainMouseOverHandler = handler;
  }
  onTrainMouseOut(handler) {
    this.onTrainMouseOutHandler = handler;
  }
  onTrainSelect(handler) {
    this.onTrainSelectedHandler = handler;
  }

  render(trainsViewModel) {
    if (trainsViewModel.changeInfo.trainsChanged) {
      this.renderTrains(trainsViewModel.trains);
    }
    if (trainsViewModel.changeInfo.sourceStationChanged) {
      this.renderSourceStation(trainsViewModel.sourceStation);
    }
    if (trainsViewModel.changeInfo.destinationStationChanged) {
      this.renderDestinationStation(trainsViewModel.destinationStation);
    }

    if (trainsViewModel.changeInfo.selectedTrainChanged) {
      this.renderSelectedTrain(trainsViewModel.selectedTrain);
    }

  }


  renderTrains(trains) {
    const tBody = this.clearTrainsTable();
    if (trains) {
      if (trains.length == 0) {
        alert("There is no train available for this stations");
        return;
      }
      document.getElementById("trainResult").setAttribute("class", "visible");
      trains.forEach((train) => this.populateRowWithTrainDetails(train, tBody));
    }


  }

  clearTrainsTable() {
    const table = document.getElementById("trainsTable");
    const tBody = table.tBodies.item(0);
    tBody.innerHTML = "";
    return tBody;
  }

  populateRowWithTrainDetails(train, tBody) {
    const row = tBody.insertRow();
    row.insertCell(0).innerHTML = train.name;
    row.insertCell(1).innerHTML = train.number;
    row.insertCell(2).innerHTML = train.sourceStation.name;
    row.insertCell(3).innerHTML = train.destinationStation.name;
    row.onclick = () => this.onTrainSelectedHandler(train);
    //row.addEventListener("click", );
    row.addEventListener("mouseenter", () => this.onTrainMouseOverHandler(train));
    row.addEventListener("mouseleave", () => {
      console.log("calling onTrainMouseOutHandler");
      this.onTrainMouseOutHandler();
    });

  }
  renderSourceStation(sourceStation) {
    if (sourceStation) {
      document.getElementById('sourceStation').value = sourceStation.name;
    }
    else {
      document.getElementById('sourceStation').value = "";
    }
  }
  renderDestinationStation(destinationStation) {
    if (destinationStation) {
      document.getElementById('destinationStation').value = destinationStation.name;
    }
    else {
      document.getElementById('destinationStation').value = "";
    }
  }
  renderSelectedTrain(selectedTrain) {
    if (selectedTrain) {
      console.log("drawlines");
      this.drawLines(selectedTrain);
    }
    else {
      console.log("remove lines");
      this.removeLines();
    }
  }


  drawLines(train) {
    var currentTrainStops = train.trainStops;
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

  removeLines() {
    if (this.stationPath) {
      this.stationPath.setMap(null);
    }
  }






}