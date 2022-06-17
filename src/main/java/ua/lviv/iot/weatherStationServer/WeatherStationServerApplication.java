package ua.lviv.iot.weatherStationServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ua.lviv.iot.weatherStationServer.controller",
		"ua.lviv.iot.weatherStationServer.logic", "ua.lviv.iot.weatherStationServer.model",
"ua.lviv.iot.weatherStationServer.datastorage"})
public class WeatherStationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherStationServerApplication.class, args);
	}

}
