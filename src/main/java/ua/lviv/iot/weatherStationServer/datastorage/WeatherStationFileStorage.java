package ua.lviv.iot.weatherStationServer.datastorage;
import org.springframework.stereotype.Component;
import ua.lviv.iot.weatherStationServer.model.WeatherStation;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


@Component
public class WeatherStationFileStorage extends Storage{
    private final String path = getPath("weatherStation");

    public List<WeatherStation> findWeatherStationInMonth() throws IOException {
        List<WeatherStation> result = new LinkedList<>();
        File file;

        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {

            if (Files.exists(Paths.get(path))) {
                file = new File(path);
                result.addAll(scanWeatherStation(file));
            }

        }
        return result;
    }

    private List<WeatherStation> scanWeatherStation(File file) throws IOException {
        List<WeatherStation> result = new LinkedList<>();
        prepareFileForReading(file);
        String [] values;
        while (scanner.hasNextLine()) {
            values = getNextLineFromFile();
            if(values != null) {
                result.add(new WeatherStation
                        (Long.valueOf(values[0]), values[1], values[2], values[3],
                                values[4]));
            }
        }
        return result;
    }


    public void saveWeatherStation(List<WeatherStation> weatherStations) throws IOException {

        File file = new File(path);
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(weatherStations.get(0).getHeaders() + "\n");
        for (WeatherStation weatherStation : weatherStations) {
            writer.write(weatherStation.toCSV() + "\n");
        }
        writer.close();

    }

}


