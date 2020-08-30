class MapView {

    constructor(onStationClick) {
        this.map = new google.maps.Map(document.getElementById('map'), {/*google.maps.map constructor The ID where the map will place,which location will be center,How much the map will be zoom*/
                    center:{lat:11.5246,lng: 77.4702},
                    zoom:7
              });
        this.onStationClick = onStationClick;
    }
    render(stations) {
        stations.forEach(addMarker);
    }

     addMarker(station){
              var circle = new google.maps.Circle({
                strokeColor: '#FF0000',
                strokeOpacity: 0.8,
                strokeWeight: 2,
                fillColor: '#FF0000',
                fillOpacity: 0.35,
                map: this.map,
                center: {lat : station.latitude, lng : station.longitude},
                radius: 3000,
                title:station.name
              });
               var name = station.name;
               var code = station.code;
               circle.addListener('click', () => this.onStationClick(station));
               circle.addListener('mouseover',function(){
                       document.getElementById('map').setAttribute('title',name);
                     });
               circle.addListener('mouseout',function(){
                       document.getElementById('map').removeAttribute('title');
                     });

        }

}