export default class TrainsViewModel {
    constructor(sourceStation, destinationStation, trains, selectedTrain) {
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.trains = trains;
        this.selectedTrain = selectedTrain;
        this.changeInfo = {};

    }
}