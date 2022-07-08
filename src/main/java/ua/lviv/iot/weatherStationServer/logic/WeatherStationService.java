package ua.lviv.iot.weatherStationServer.logic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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


    private final HashMap<Long, WeatherStation> weatherStations = new HashMap<>();


    @Autowired
    WeatherStationFileStorage weatherStationStore;


    public List<WeatherStation> getWeatherStations() {

        return new LinkedList<>(this.weatherStations.values());
    }

    public WeatherStation getWeatherStationById(Long weatherStationId) {
        if (weatherStations.containsKey(weatherStationId)){
            return weatherStations.get(weatherStationId);
        } else {
            return null;
        }
    }

    public void addWeatherStation(WeatherStation weatherStation) {
        weatherStations.put(weatherStation.getWeatherStationId(), weatherStation);
    }

    public void updateWeatherStation(WeatherStation weatherStation, Long weatherStationId) {

        WeatherStation newWeatherStation = new WeatherStation();

        this.weatherStations.remove(weatherStationId);

        newWeatherStation.setWeatherStationId(weatherStation.getWeatherStationId());

        newWeatherStation.setManufactur(weatherStation.getManufactur());

        newWeatherStation.setGpcOfWeatherStation(weatherStation.getGpcOfWeatherStation());

        newWeatherStation.setDataOfInstallation(weatherStation.getDataOfInstallation());

        newWeatherStation.setLocationOfWeatherStation(weatherStation.getLocationOfWeatherStation());

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
