package ua.lviv.iot.weatherStationServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.weatherStationServer.logic.WeatherStationService;
import ua.lviv.iot.weatherStationServer.model.WeatherStation;


import java.util.List;

@RestController
@RequestMapping("/weatherStation")
public class WeatherStationController {

    @Autowired
    private WeatherStationService weatherStationService;

    @GetMapping
    public List<WeatherStation> getWeatherStations() {
        return weatherStationService.getWeatherStations();
    }

    @GetMapping("/{weatherStationId}")
    public WeatherStation getWeatherStationById(@PathVariable Long weatherStationId) {
        return weatherStationService.getWeatherStationById(weatherStationId);
    }

    @PostMapping
    public void addWeatherStation(@RequestBody WeatherStation weatherStation) {
        weatherStationService.addWeatherStation(weatherStation);
    }

    @PutMapping("/{weatherStationId}")
    public void updateWeatherStation(@RequestBody WeatherStation weatherStation,
                                         @PathVariable Long weatherStationId) {
        weatherStationService.updateWeatherStation(weatherStation, weatherStationId);
    }

    @DeleteMapping("/{weatherStationId}")
    public void deleteWeatherStation(@PathVariable Long weatherStationId) {
        weatherStationService.deleteWeatherStation(weatherStationId);
    }
}
