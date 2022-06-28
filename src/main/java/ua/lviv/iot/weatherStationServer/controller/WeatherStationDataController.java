package ua.lviv.iot.weatherStationServer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.weatherStationServer.logic.WeatherStationDataService;
import ua.lviv.iot.weatherStationServer.model.WeatherStationData;



import java.util.List;

@RestController
@RequestMapping("/weatherStationData")
public class WeatherStationDataController {

    @Autowired
    private WeatherStationDataService weatherStationDataService;

    @GetMapping
    public List<WeatherStationData> getWeatherStationDatas(){
        return weatherStationDataService.getWeatherStationDatas();
    }

    @GetMapping("/{weatherStationId}")
    public WeatherStationData getWeatherStationDataById(@PathVariable Long weatherStationId) {
        return weatherStationDataService.getWeatherStationDataById(weatherStationId);
    }

    @PostMapping
    public void addWeatherStationData(@RequestBody WeatherStationData weatherStationData) {
        weatherStationDataService.addWeatherStationData(weatherStationData);
    }

    @PutMapping("/{weatherStationId}")
    public void updateWeatherStationData(@RequestBody WeatherStationData weatherStationData,
                                         @PathVariable Long weatherStationId) {
        weatherStationDataService.updateWeatherStationData(weatherStationData, weatherStationId);
    }

    @DeleteMapping("/{weatherStationId}")
    public void deleteWeatherStationData(@PathVariable Long weatherStationId) {
        weatherStationDataService.deleteWeatherStationData(weatherStationId);
    }








}
