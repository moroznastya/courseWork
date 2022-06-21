package ua.lviv.iot.weatherStationServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class WeatherStation extends WeatherStationInfo {

    private Long weatherStationId;


    public String getHeaders() {
        return "weatherStationId, manufactur, gpcOfWeatherStation, " +
                "dataOfInstallation, locationOfWeatherstation, dataOfServiceWorks, "
                + "descriptionOfServiceWorks";
    }

    public String toCSV() {
        return weatherStationId + ", " + manufactur + ", " + gpcOfWeatherStation + ", "
                + dataOfInstallation + ", " + locationOfWeatherStation + ", "
                + dataOfServiceWorks + ", " + descriptionOfServiceWorks;
    }
}

