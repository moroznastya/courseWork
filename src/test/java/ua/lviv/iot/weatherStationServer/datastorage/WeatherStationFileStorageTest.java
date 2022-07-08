package ua.lviv.iot.weatherStationServer.datastorage;

import org.junit.jupiter.api.Assertions;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class WeatherStationFileStorageTest {


    public String getAllLines(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line;
        StringBuilder allLines = new StringBuilder();
        while ((line = br.readLine()) != null) {
            allLines.append(line);
        }
        return allLines.toString();
    }

    @org.junit.jupiter.api.Test
    public void saveWeatherStation()throws IOException{
      Assertions.assertEquals(getAllLines
                      ("C:\\Users\\Admin\\IdeaProjects\\weatherStationServerr\\weatherStationServer" +
                              "\\src\\test\\resources\\expectedResult.csv"),
               getAllLines("C:\\Users\\Admin\\IdeaProjects\\weatherStationServerr\\files" +
                       "\\weatherStation-2022-06-16.csv"));

    }


}