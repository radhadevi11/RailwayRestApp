export default class TrainStopsController {
    constructor(trainStopsView, trainStopsViewModel) {
        this.trainStopsView = trainStopsView;
        this.trainStopsViewModel = trainStopsViewModel;
        
    }

    onTrainClick(train) {
        this.trainStopsViewModel.trainStops = train.trainStops;
        this.trainStopsView.render(this.trainStopsViewModel);
    }

}