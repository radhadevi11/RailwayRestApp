export default class TrainStopsView {
    constructor() {
        const closePopUpBtn = document.getElementById('closePopUp');
        closePopUpBtn.addEventListener('click', () => this.closeStopsPopUp());
    }
    render(trainStopsViewModel) {
        if (trainStopsViewModel.trainStops) {
            this.showStops(trainStopsViewModel.trainStops);
        }

    }
    showStops(trainStops) {

        const currentTrainStops = trainStops;
        const table = document.getElementById("trainStops");
        /*for(let i = 0; i < table.rows.length; i++) {
          table.deleteRow(0);
        }*/
        const tBody = table.tBodies.item(0);
        tBody.innerHTML = "";
        const populateRow = (trainStop) => {
            this.populateRowWithTrainStopDetails(tBody, trainStop);
        }
        currentTrainStops.forEach(populateRow);
        this.openStopsPopUp();

    }
    openStopsPopUp() {
        document.getElementById('stops').setAttribute('class', 'visible');
    }
    closeStopsPopUp() {
        document.getElementById('stops').setAttribute('class', 'hidden');
    }

    populateRowWithTrainStopDetails(tBody, trainStop) {
        const row = tBody.insertRow();
        row.insertCell(0).innerHTML = trainStop.arrivalTime;
        row.insertCell(1).innerHTML = trainStop.departureTime;
        row.insertCell(2).innerHTML = trainStop.sequence;
        row.insertCell(3).innerHTML = trainStop.stationName;
    }

}