class AppView {
    constructor() {
     this.mapView = new MapView((station) => this.onStationClick(station));
     //this.trainView = new TrainsView((train) => this.onTrainMouseOver(train), () => this.onTrainMouseOut());

    }
    onStationClick(station) {
        this.trainView.onStationClicked(station);
    }

    /*onTrainMouseOver(train) {
        this.mapView.drawLines(train);
    }
    onTrainMouseOut() {
        this.mapView.removeLine();
    }*/


    render() {
        this.mapView.render(this.getAllStations());
       // this.trainView.render();
    }

    

}