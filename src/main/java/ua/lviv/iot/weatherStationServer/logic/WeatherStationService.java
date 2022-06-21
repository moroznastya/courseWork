package ua.lviv.iot.weatherStationServer.logic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.weatherStationServer.datastorage.WeatherStationFileStorage;
import ua.lviv.iot.weatherStationServer.model.WeatherStation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class WeatherStationService {


    HashMap<Long, WeatherStation> weatherStations = new HashMap<>();


    @Autowired
    WeatherStationFileStorage weatherStationStore =new WeatherStationFileStorage();
    //WeatherStationFileStorage weatherStationFileStorage;


    public List<WeatherStation> getWeatherStations() {

        return new LinkedList<>(this.weatherStations.values());
    }

    public WeatherStation getWeatherStationById(Long weatherStationId) {

        return weatherStations.get(weatherStationId);
    }

    public void addWeatherStation(WeatherStation weatherStation) {
        weatherStations.put(weatherStation.getWeatherStationId(), weatherStation);
    }

    public void updateWeatherStation(WeatherStation weatherStation, Long weatherStationId) {
        WeatherStation oldWeatherStation = this.weatherStations.get(weatherStationId);
        WeatherStation newWeatherStation = new WeatherStation();

        this.weatherStations.remove(weatherStationId);

        newWeatherStation.setWeatherStationId(oldWeatherStation.getWeatherStationId());


        if (weatherStation.getManufactur() != null) {
            newWeatherStation.setManufactur(weatherStation.getManufactur());
        } else {
            newWeatherStation.setManufactur(oldWeatherStation.getManufactur());
        }

        if (weatherStation.getGpcOfWeatherStation() != null) {
            newWeatherStation.setGpcOfWeatherStation(weatherStation.getGpcOfWeatherStation());
        } else {
            newWeatherStation.setGpcOfWeatherStation(oldWeatherStation.getGpcOfWeatherStation());
        }

        if (weatherStation.getDataOfInstallation() != null) {
            newWeatherStation.setDataOfInstallation(weatherStation.getDataOfInstallation());
        } else {
            newWeatherStation.setDataOfInstallation(oldWeatherStation.getDataOfInstallation());
        }

        if (weatherStation.getLocationOfWeatherStation() != null) {
            newWeatherStation.setLocationOfWeatherStation(weatherStation.getLocationOfWeatherStation());
        } else {
            newWeatherStation.setLocationOfWeatherStation(oldWeatherStation.getLocationOfWeatherStation());
        }

        if (weatherStation.getDataOfServiceWorks() != null) {
            newWeatherStation.setDataOfServiceWorks(weatherStation.getDataOfServiceWorks());
        } else {
            newWeatherStation.setDataOfServiceWorks(oldWeatherStation.getDataOfServiceWorks());
        }

        if (weatherStation.getDescriptionOfServiceWorks() != null) {
            newWeatherStation.setDescriptionOfServiceWorks(weatherStation.getDescriptionOfServiceWorks());
        } else {
            newWeatherStation.setDescriptionOfServiceWorks(oldWeatherStation.getDescriptionOfServiceWorks());
        }


        this.weatherStations.put(newWeatherStation.getWeatherStationId(), newWeatherStation);
    }

    public void deleteWeatherStation(Long weatherStationId) {

        this.weatherStations.remove(weatherStationId);
    }



    @PreDestroy
    private void saveWeatherStations() throws IOException {
        List<WeatherStation> list = this.weatherStations.values().stream().toList();
        weatherStationStore.saveWeatherStation(list);
    }

    @PostConstruct
    private void fillWeatherStations() throws IOException {
        if (weatherStationStore.findWeatherStationInMonth() != null) {
            List<WeatherStation> list = weatherStationStore.findWeatherStationInMonth();
            for (WeatherStation weatherStation: list) {
                this.weatherStations.put(weatherStation.getWeatherStationId(), weatherStation);
            }
        }
    }
}
