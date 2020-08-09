
class Station{
    constructor(code,name, latitude, longitude){
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
  
    addMarker(map){
          var circle = new google.maps.Circle({
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35,
            map: map,
            center: {lat : this.latitude, lng : this.longitude},
            radius: 3000,
            title:this.name
          });
           var name = this.name;
           var code = this.code;
           circle.addListener('click', function() {
                      if(document.getElementById('sourceStation').value==""){
                         document.getElementById('sourceStation').value=name;
                         document.getElementById('sourceStationCode').value=code;
                    }
                      else if(document.getElementById('destinationStation').value==""){
                        document.getElementById('destinationStation').value=name;
                          document.getElementById('destinationStationCode').value=code;
                      }

                });
           circle.addListener('mouseover',function(){
                   document.getElementById('map').setAttribute('title',name);
                 });
           circle.addListener('mouseout',function(){
                   document.getElementById('map').removeAttribute('title');
                 });

    }
}

        
   
  