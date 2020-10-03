export default class TrainStop {
  constructor(arrivalTime, departureTime, sequence, stationName, distance, latitude, longitude) {
    this.arrivalTime = arrivalTime;
    this.departureTime = departureTime;
    this.sequence = sequence;
    this.stationName = stationName;
    this.distance = distance;
    this.latitude = latitude;
    this.longitude = longitude;

  }
}