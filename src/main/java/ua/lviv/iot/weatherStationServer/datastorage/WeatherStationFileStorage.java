package ua.lviv.iot.weatherStationServer.datastorage;
import org.springframework.stereotype.Component;
import ua.lviv.iot.weatherStationServer.model.WeatherStation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Component
public class WeatherStationFileStorage {


    public List<WeatherStation> findWeatherStationInMonth() throws IOException {
        List<WeatherStation> result = new LinkedList<>();
        File file;

        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {

            if (Files.exists(Paths.get("files//" + "weatherStation-" + "-" + LocalDate.now() + ".csv"))) {
                file = new File("files//" + "weatherStation-" + LocalDate.now() + ".csv");
                result.addAll(scanWeatherStation(file));
            }

        }
        return result;
    }

    private List<WeatherStation> scanWeatherStation(File file) throws IOException {
        List<WeatherStation> result = new LinkedList<>();
        boolean isFirst = true;
        Scanner scanner = new Scanner(file, "UTF_8");
        while (scanner.hasNextLine()) {
            if (!isFirst) {
                List<String> values = Arrays.stream(scanner.nextLine().split(", ")).toList();
                result.add(fillWeatherStation(values));
            } else {
                scanner.nextLine();
                isFirst = false;
            }
        }
        return result;
    }


    public WeatherStation fillWeatherStation(List<String> values) {
        WeatherStation weatherStation = new WeatherStation();
        int index = 0;
        for (String value : values) {
            switch (index) {
                case 0 -> weatherStation.setWeatherStationId(Long.parseLong(value));

                case 1 -> weatherStation.setManufactur(value);

                case 2 -> weatherStation.setGpcOfWeatherStation(value);

                case 3 -> weatherStation.setDataOfInstallation(value);

                case 4 -> weatherStation.setLocationOfWeatherStation(value);

                case 5 -> weatherStation.setDataOfServiceWorks(value);

                case 6 -> weatherStation.setDescriptionOfServiceWorks(value);
            }
            index++;

        }
        return weatherStation;

    }
    public void saveWeatherStation(List<WeatherStation> weatherStations) throws IOException {


        File file = new File("files//" + "weatherStation-" + LocalDate.now() + ".csv");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        writer.write(weatherStations.get(0).getHeaders() + "\n");
        for (WeatherStation weatherStation : weatherStations) {
            writer.write(weatherStation.toCSV() + "\n");
        }
        writer.close();

    }

}


