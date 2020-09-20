
export default class TrainsController {
        constructor(trainsView, trainsViewModel) {
                this.trainsView = trainsView;
                this.trainsViewModel = trainsViewModel;
                trainsView.onGetTrainsClick(() => this.getAllTrains());
                trainsView.onResetClick(() => this.clearInput());
                trainsView.onTrainMouseOver((train) => this.onTrainMouseOver(train));
                trainsView.onTrainMouseOut(() => this.onTrainMouseOut);
        }


        onStationClick(station) {
                if (!this.trainsViewModel.sourceStation) {
                       this.trainsViewModel.sourceStation = station;
                }
                else if (!this.trainsViewModel.destinationStation) {
                 this.trainsViewModel.destinationStation = station;
                }
                else {
                    return;
                }
                this.trainsView.render(this.trainsViewModel);

        }
        clearInput() {
            this.trainsViewModel.trains = null;
            this.trainsViewModel.sourceStation = null;
            this.trainsViewModel.destinationStation = null;
            this.trainsView.render(this.trainsViewModel);


        }
        onTrainMouseOver(train) {
            this.trainsViewModel.selectedTrain = train;
            this.trainsView.render(this.trainsViewModel);
        }
         onTrainMouseOut(train) {
            this.trainsViewModel.selectedTrain = null;
            this.trainsView.render(this.trainsViewModel);
         }

        getAllTrains() {
                var request = new XMLHttpRequest();
                request.open('GET', 'http://localhost:8080/trains/' + this.trainsViewModel.sourceStation.code +
                        '/' + this.trainsViewModel.destinationStation.code, false);
                request.send(null);
                var trainsJsonArray = JSON.parse(request.responseText);//loop through this json array,
                //and convert each json objects to a station object add a station object to a station array
                //update model
                this.trainsViewModel.trains = trainsJsonArray.map((trainJson) => this.convertToTrain(trainJson));
                //render view
                this.trainsView.render(this.trainsViewModel);

        }
        convertToTrain(trainJson) {
                return new Train(trainJson.number,
                        trainJson.name,
                        trainJson.sourceStation,
                        trainJson.destinationStation,
                        trainJson.trainStops.map(this.convertToTrainStop));


        }
        convertToTrainStop = trainStopJson =>
                new TrainStop(trainStopJson.arrivalTime,
                        trainStopJson.departureTime,
                        trainStopJson.sequence,
                        trainStopJson.station.name,
                        trainStopJson.distance,
                        trainStopJson.station.latitude,
                        trainStopJson.station.longitude);


}