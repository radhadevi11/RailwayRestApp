export default class MapViewController {
    constructor(mapView, mapViewModel) {
        this.mapView = mapView;
        this.mapViewModel = mapViewModel;
        this.mapView.render(this.mapViewModel);
    }

    onTrainMouseOver(train) {
        this.mapViewModel.train = train;
        this.mapView.renderTrain(mapViewModel);
    }
    onTrainMouseOut() {
        this.mapViewModel.train = null;
        this.mapView.renderTrain(this.mapViewModel);
    }

}