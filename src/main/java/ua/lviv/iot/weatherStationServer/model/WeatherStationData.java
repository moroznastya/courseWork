package ua.lviv.iot.weatherStationServer.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class WeatherStationData {
    private Long weatherStationDataId;
    public String temperature;
    public String humidity;
    public String atmosphericPressure;
    public String speedOfWind;
    public String directionOfWind;



    public String getHeaders() {
        return "weatherStationDataId " +
                "temperature "+"humidity "+"atmosphericPressure "+
                "speedOfWind " + "directionOfWind";
    }

    public String toCSV() {
        return weatherStationDataId + ", " + temperature + ", "
                + humidity + ", " + atmosphericPressure + ", " +
                speedOfWind + ", " + directionOfWind;
    }




}
