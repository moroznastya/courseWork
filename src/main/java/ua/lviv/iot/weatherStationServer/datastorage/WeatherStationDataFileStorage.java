package ua.lviv.iot.weatherStationServer.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.weatherStationServer.model.WeatherStationData;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Component
public class WeatherStationDataFileStorage extends Storage {
    private final String path = getPath("weatherStationData");

    public List<WeatherStationData> findWeatherStationDataInMonth() throws IOException {
        List<WeatherStationData> result = new LinkedList<>();
        File file;
        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {

            if (Files.exists(Paths.get(path))) {
                file = new File(path);
                result.addAll(scanWeatherStationData(file));
            }

        }
        return result;
    }

    private List<WeatherStationData> scanWeatherStationData(File file) throws IOException {
        List<WeatherStationData> result = new LinkedList<>();
        prepareFileForReading(file);
        String [] values;
        while (scanner.hasNextLine()) {
            values = getNextLineFromFile();
            if(values != null) {
                result.add(new WeatherStationData
                        (Long.valueOf(values[0]), values[1], values[2], values[3],
                    values[4], values[5]));
            }
        }
        return result;
    }

    public void saveWeatherStationData(List<WeatherStationData> weatherStationDatas) throws IOException {

        File file = new File("files//" + "weatherStation-" + LocalDate.now() + ".csv");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(weatherStationDatas.get(0).getHeaders() + "\n");
        for (WeatherStationData weatherStationData : weatherStationDatas) {
            writer.write(weatherStationData.toCSV() + "\n");
        }
        writer.close();

    }
}
