package ua.lviv.iot.weatherStationServer.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public WeatherStationData getWeatherStationDataById(Long weatherStationDataId) {
        return weatherStationDatas.get(weatherStationDataId);
    }

    public void addWeatherStationData(WeatherStationData weatherStationData) {
        weatherStationDatas.put(weatherStationData.getWeatherStationId(), weatherStationData);
    }

    public void updateWeatherStationData(WeatherStationData weatherStationData, Long weatherStationDataId) {
        WeatherStationData oldWeatherStationData = this.weatherStationDatas.get(weatherStationDataId);
        WeatherStationData newWeatherStationData = new WeatherStationData();

        this.weatherStationDatas.remove(weatherStationDataId);

        newWeatherStationData.setWeatherStationId(oldWeatherStationData.getWeatherStationId());


        if (weatherStationData.getTemperature() != null) {
            newWeatherStationData.setTemperature(weatherStationData.getTemperature());
        } else {
            newWeatherStationData.setTemperature(oldWeatherStationData.getTemperature());
        }


        newWeatherStationData.setHumidity(weatherStationData.getHumidity());

        newWeatherStationData.setAtmosphericPressure(weatherStationData.getAtmosphericPressure());

        newWeatherStationData.setSpeedOfWind(weatherStationData.getSpeedOfWind());

        if (weatherStationData.getDirectionOfWind() != null) {
            newWeatherStationData.setDirectionOfWind(weatherStationData.getDirectionOfWind());
        } else {
            newWeatherStationData.setDirectionOfWind(oldWeatherStationData.getDirectionOfWind());
        }

        this.weatherStationDatas.put(newWeatherStationData.getWeatherStationId(), newWeatherStationData);
    }

    public void deleteWeatherStationData(Long weatherStationDataId) {
        this.weatherStationDatas.remove(weatherStationDataId);
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
