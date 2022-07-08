package ua.lviv.iot.weatherStationServer.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ua.lviv.iot.weatherStationServer.datastorage.WeatherStationDataFileStorage;
import ua.lviv.iot.weatherStationServer.model.WeatherStationData;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class WeatherStationDataService {
    private final HashMap<Long, WeatherStationData> weatherStationDatas = new HashMap<>();


    @Autowired
     private WeatherStationDataFileStorage weatherStationDataFileStorage;


    public List<WeatherStationData> getWeatherStationDatas() {
        return new LinkedList<>(this.weatherStationDatas.values());
    }

    public WeatherStationData getWeatherStationDataById(Long weatherStationId) {
        if (weatherStationDatas.containsKey(weatherStationId)){
            return weatherStationDatas.get(weatherStationId);
        } else {
            return null;
        }
    }

    public void addWeatherStationData(WeatherStationData weatherStationData) {
        weatherStationDatas.put(weatherStationData.getWeatherStationId(), weatherStationData);
    }

    public void updateWeatherStationData(WeatherStationData weatherStationData, Long weatherStationId) {

        WeatherStationData newWeatherStationData = new WeatherStationData();

        this.weatherStationDatas.remove(weatherStationId);

        newWeatherStationData.setWeatherStationId(weatherStationData.getWeatherStationId());

        newWeatherStationData.setTemperature(weatherStationData.getTemperature());

        newWeatherStationData.setHumidity(weatherStationData.getHumidity());

        newWeatherStationData.setAtmosphericPressure(weatherStationData.getAtmosphericPressure());

        newWeatherStationData.setSpeedOfWind(weatherStationData.getSpeedOfWind());

        newWeatherStationData.setDirectionOfWind(weatherStationData.getDirectionOfWind());

        this.weatherStationDatas.put(newWeatherStationData.getWeatherStationId(), newWeatherStationData);
    }

    public void deleteWeatherStationData(Long weatherStationId) {
        this.weatherStationDatas.remove(weatherStationId);
    }




    @PreDestroy
    private void saveWeatherStationDatas() throws IOException{
        List<WeatherStationData> list = this.weatherStationDatas.values().stream().toList();
        weatherStationDataFileStorage.saveWeatherStationData(list);
    }

    @PostConstruct
    private void loadWeatherStationDatas() throws IOException {
        if (weatherStationDataFileStorage.findWeatherStationDataInMonth() != null) {
            List<WeatherStationData> list = weatherStationDataFileStorage.findWeatherStationDataInMonth();
            for (WeatherStationData weatherStationData: list) {
                this.weatherStationDatas.put(weatherStationData.getWeatherStationId(), weatherStationData);
            }
        }
    }

}
