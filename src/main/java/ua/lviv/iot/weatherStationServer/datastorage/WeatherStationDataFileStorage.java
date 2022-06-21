package ua.lviv.iot.weatherStationServer.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.weatherStationServer.model.WeatherStationData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Component
public class WeatherStationDataFileStorage {


    public List<WeatherStationData> findWeatherStationDataInMonth() throws IOException {
        List<WeatherStationData> result = new LinkedList<>();
        File file;

        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {

            if (Files.exists(Paths.get("files//" + "weatherStationData-" + "-" + LocalDate.now() + ".csv"))) {
                file = new File("files//" + "weatherStationData-" + LocalDate.now() + ".csv");
                result.addAll(scanWeatherStationData(file));
            }

        }
        return result;
    }

    private List<WeatherStationData> scanWeatherStationData(File file) throws IOException {
        List<WeatherStationData> result = new LinkedList<>();
        boolean isFirst = true;
        Scanner scanner = new Scanner(file, "UTF_8");
        while (scanner.hasNextLine()) {
            if (!isFirst) {
                List<String> values = Arrays.stream(scanner.nextLine().split(", ")).toList();
                result.add(fillWeatherStationData(values));
            } else {
                scanner.nextLine();
                isFirst = false;
            }
        }
        return result;
    }


    public WeatherStationData fillWeatherStationData(List<String> values) {
        WeatherStationData weatherStationData = new WeatherStationData();
        int index = 0;
        for (String value : values) {
            switch (index) {
                case 0 -> weatherStationData.setWeatherStationDataId(Long.parseLong(value));
                case 1 -> weatherStationData.setTemperature(value);
                case 2 -> weatherStationData.setHumidity(value);
                case 3 -> weatherStationData.setAtmosphericPressure(value);
                case 4 -> weatherStationData.setSpeedOfWind(value);
                case 5 -> weatherStationData.setDirectionOfWind(value);

            }
            index++;

        }
        return weatherStationData;

    }


    public void saveWeatherStationData(List<WeatherStationData> weatherStationDatas) throws IOException {

        File file = new File("files//" + "weatherStation-" + LocalDate.now() + ".csv");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        writer.write(weatherStationDatas.get(0).getHeaders() + "\n");
        for (WeatherStationData weatherStationData : weatherStationDatas) {
            writer.write(weatherStationData.toCSV() + "\n");
        }
        writer.close();

    }
}
