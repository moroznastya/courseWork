package ua.lviv.iot.weatherStationServer.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.weatherStationServer.DateNow;
import ua.lviv.iot.weatherStationServer.model.WeatherStation;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Component
public class WeatherStationFileStorage {
    public List<WeatherStation> fillWeatherStation() throws IOException {
        List<WeatherStation> result = new LinkedList<>();
        File file;
        String year = Integer.toString(LocalDate.now().getYear());
        String month;

        if (LocalDate.now().getMonthValue() < 10) {
            month = "0" + LocalDate.now().getMonthValue();
        } else {
            month = Integer.toString(LocalDate.now().getMonthValue());
        }

        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {
            if (i < 10) {
                if (Files.exists(Paths.get("files//"+"weatherStation-" + year + "-" + month + "-0" + i + ".csv"))) {
                    file = new File("files//"+"weatherStation-" + year + "-" + month + "-0" + i + ".csv");
                    result.addAll(scanWeatherStation(file));
                }
            } else {
                if (Files.exists(Paths.get("files//"+"weatherStation-" + year + "-" + month + "-" + i + ".csv"))) {
                    file = new File("files//"+"weatherStation-" + year + "-" + month + "-" + i + ".csv");
                    result.addAll(scanWeatherStation(file));
                }
            }
        }
        return result;
    }

    private List<WeatherStation> scanWeatherStation(File CSV) throws IOException {
        List<WeatherStation> result = new LinkedList<>();
        boolean isFirst = true;
        Scanner scanner = new Scanner(CSV, StandardCharsets.UTF_8);
        while (scanner.hasNextLine()) {
            if (!isFirst) {
                List<String> values = Arrays.stream(scanner.nextLine().split(", ")).toList();
                WeatherStation weatherStation = new WeatherStation();
                int index = 0;
                for (String value : values) {
                    switch (index) {
                        case 0 -> weatherStation.setWeatherStationId(Long.parseLong(value));

                        case 1 -> weatherStation.setManufactur(value);

                        case 2 -> weatherStation.setGpcOfWeatherStation(value);

                        case 3 -> weatherStation.setDataOfInstallaton(value);

                        case 4 -> weatherStation.setLocatoinOfWeatherstation(value);

                        case 5 -> weatherStation.setLocatoinOfWeatherstation(value);

                        case 6 -> weatherStation.setDataOfServiceWorks(value);

                        case 7 -> weatherStation.setDescriptionOfServiceWorks(value);
                    }
                    index++;
                }
                result.add(weatherStation);
            } else {
                scanner.nextLine();
                isFirst = false;
            }
        }
        return result;
    }


    public void saveWeatherStation(List<WeatherStation> weatherStations) throws IOException {
        String date = DateNow.getTimeNow();

        File file = new File("files//" +"weatherStation-" + date + ".csv");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(weatherStations.get(0).getHeaders() + "\n");
            for (WeatherStation weatherStation : weatherStations) {
                writer.write(weatherStation.toCSV() + "\n");
            }

        }

    }
}
