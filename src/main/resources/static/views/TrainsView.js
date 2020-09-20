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

   render(trainsViewModel) {
        if(trainsViewModel.trains) {
            this.renderTrains(trainsViewModel.trains);
        }
        else {
            this.clearTrainsTable();
        }
        if(trainsViewModel.sourceStation) {
            document.getElementById('sourceStation').value = trainsViewModel.sourceStation.name;
        }
        else {
            document.getElementById('sourceStation').value = "";
        }
        if(trainsViewModel.destinationStation) {
            document.getElementById('destinationStation').value = trainsViewModel.destinationStation.name;
        }
        else {
            document.getElementById('destinationStation').value = "";
        }
        if(trainsViewModel.selectedTrain) {
            this.drawLines(trainsViewModel.selectedTrain);
        }
        else {
            this.removeLines();
        }

    }


    renderTrains(trains) {
    if (trains.length == 0) {
      alert("There is no train available for this stations");
      return;
    }
    document.getElementById("trainResult").setAttribute("class", "visible");
    const tBody = this.clearTrainsTable();
    trains.forEach((train) => this.populateRowWithTrainDetails(train, tBody));
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
    row.addEventListener("mouseover", () => this.onTrainMouseOverHandler(train));
    row.addEventListener("mouseout", () => this.onTrainMouseOutHandler());
    row.addEventListener("click",  () => onTrainSelected(train));
  }


  drawLines(train)  {
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
  showStops(train) {
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

    const currentTrainStops = train.trainStops;
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

  }





}