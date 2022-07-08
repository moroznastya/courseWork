package ua.lviv.iot.weatherStationServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class WeatherStation  {

    private Long weatherStationId;
    private String manufactur;
    private String gpcOfWeatherStation;
    private String dataOfInstallation;
    private String locationOfWeatherStation;



    public String getHeaders() {
        return "weatherStationId, manufactur, gpcOfWeatherStation, " +
                "dataOfInstallation, locationOfWeatherstation";
    }

    public String toCSV() {
        return weatherStationId + ", " + manufactur + ", " + gpcOfWeatherStation + ", "
                + dataOfInstallation + ", " + locationOfWeatherStation;
    }
}

