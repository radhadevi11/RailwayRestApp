import Train from '../train.js';
import TrainStop from '../trainStop.js';

export default class TrainsController {
        constructor(trainsView, trainsViewModel) {
                this.trainsView = trainsView;
                this.trainsViewModel = trainsViewModel;
                trainsView.onGetTrainsClick(() => this.getAllTrains());
                trainsView.onResetClick(() => this.clearInput());
                trainsView.onTrainMouseOver((train) => this.onTrainMouseOver(train));
                trainsView.onTrainMouseOut(() => this.onTrainMouseOut());

        }


        onStationClick(station) {
                if (!this.trainsViewModel.sourceStation) {
                        this.trainsViewModel.sourceStation = station;
                        this.trainsViewModel.changeInfo = { sourceStationChanged: true };
                }
                else if (!this.trainsViewModel.destinationStation) {
                        this.trainsViewModel.destinationStation = station;
                        this.trainsViewModel.changeInfo = { destinationStationChanged: true };
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
                this.trainsViewModel.changeInfo = {
                        sourceStationChanged: true, destinationStationChanged: true,
                        trainsChanged: true
                };
                this.trainsView.render(this.trainsViewModel);


        }
        onTrainMouseOver(train) {
                this.trainsViewModel.selectedTrain = train;
                this.trainsViewModel.changeInfo = { selectedTrainChanged: true };
                this.trainsView.render(this.trainsViewModel);
        }
        onTrainMouseOut() {
                this.trainsViewModel.selectedTrain = null;
                this.trainsViewModel.changeInfo = { selectedTrainChanged: true };
                this.trainsView.render(this.trainsViewModel);
        }

        getAllTrains() {
                window.fetch('http://localhost:8080/trains/' + this.trainsViewModel.sourceStation.code +
                        '/' + this.trainsViewModel.destinationStation.code)
                        .then(response => response.json())
                        .then(trainsJsonArray => {
                                this.trainsViewModel.trains = trainsJsonArray.map((trainJson) => this.convertToTrain(trainJson));
                                //render view
                                this.trainsViewModel.changeInfo = { trainsChanged: true };
                                this.trainsView.render(this.trainsViewModel);
                        })


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