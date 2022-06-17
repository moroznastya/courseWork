package ua.lviv.iot.weatherStationServer.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStationInfo {
    public String manufactur;
    public String gpcOfWeatherStation;
    public String dataOfInstallaton;
    public String locatoinOfWeatherstation;
    public String dataOfServiceWorks;
    public String descriptionOfServiceWorks;

}
