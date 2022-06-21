package ua.lviv.iot.weatherStationServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStationInfo {
    public String manufactur;
    public String gpcOfWeatherStation;
    public String dataOfInstallation;
    public String locationOfWeatherStation;
    public String dataOfServiceWorks;
    public String descriptionOfServiceWorks;

}
