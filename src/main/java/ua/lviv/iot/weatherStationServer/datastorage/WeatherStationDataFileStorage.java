package ua.lviv.iot.weatherStationServer.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.weatherStationServer.DateNow;
import ua.lviv.iot.weatherStationServer.model.WeatherStationData;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


@Component
public class WeatherStationDataFileStorage {



    public List<WeatherStationData> fillWeatherStationDatas() throws IOException, ParseException {
        List<WeatherStationData> resultList = new LinkedList<>();


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
                if (Files.exists(Paths.get("files\\" + "weatherStationData-" + year + "-" + month + "-0" + i + ".csv"))) {
                    file = new File("files\\" +  "weatherStationData-" + year + "-" + month + "-0" + i + ".csv");
                    resultList.addAll(scanWeatherStationData(file));
                }
            } else {
                if (Files.exists(Paths.get("files\\" +  "weatherStationData-" + year + "-" + month + "-" + i + ".csv"))) {
                    file = new File("files\\" + "weatherStationData-" + year + "-" + month + "-" + i + ".csv");
                    resultList.addAll(scanWeatherStationData(file));
                }
            }
        }

        return resultList;
    }

    private List<WeatherStationData> scanWeatherStationData(File file) throws ParseException, IOException {
        List<WeatherStationData> resultWeatherStationDatas = new LinkedList<>();
        Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
        boolean isFirst = true;
        while (scanner.hasNextLine()) {
            if (!isFirst) {
                List<String> values = Arrays.stream(scanner.nextLine().split(", ")).toList();
                WeatherStationData weatherStationData = new WeatherStationData();
                int index = 0;
                for (String value: values) {
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
                resultWeatherStationDatas.add(weatherStationData);
            } else {
                scanner.nextLine();
                isFirst = false;
            }
        }
        return resultWeatherStationDatas;
    }


    public void saveWeatherStationData(final List<WeatherStationData> weatherStationDatas) throws IOException{
        String date = DateNow.getTimeNow();
        File file = new File("files\\" + "weatherStationData-" + date + ".csv");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        writer.write(weatherStationDatas.get(0).getHeaders() + "\n");
        for (WeatherStationData weatherStationData: weatherStationDatas) {
            writer.write(weatherStationData.toCSV() + "\n");
        }
        writer.close();

    }
}
