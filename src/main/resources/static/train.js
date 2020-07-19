    class Train{
    constructor(number,name,sourceStation,destinationStation,trainStops){
      this.number = number;
      this.name = name;
      this.sourceStation = sourceStation;
      this.destinationStation = destinationStation;
      this.trainStops = trainStops;

      }
    }
    class TrainStop{
    	constructor(arrivalTime,departureTime,sequence,stationName,distance,latLng){
    		this.arrivalTime = arrivalTime;
    		this.departureTime = departureTime;
    		this.sequence = sequence;
    		this.stationName = stationName;
    		this.distance = distance;
    		this.latLng = latLng;

    	}
    }
   
