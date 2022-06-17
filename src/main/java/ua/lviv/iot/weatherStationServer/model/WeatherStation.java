package ua.lviv.iot.weatherStationServer.model;

import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class WeatherStation extends WeatherStationInfo {

    private Long weatherStationId;


    public String getHeaders() {
        return "weatherStationId, manufactur, gpcOfWeatherStation, " +
                "dataOfInstallaton, locatoinOfWeatherstation, dataOfServiceWorks"
                + " descriptionOfServiceWorks";
    }

    public String toCSV() {
        return weatherStationId + ", " + manufactur + ", " + gpcOfWeatherStation + ", "
                + dataOfInstallaton + ", " + locatoinOfWeatherstation + ", "
                + dataOfServiceWorks + ", " + descriptionOfServiceWorks;
    }
}

