package ua.lviv.iot.weatherStationServer.datastorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MaintenanceJobFileStorageTest {

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

    @Test
    void saveMaintenanceJob() throws IOException{
        Assertions.assertEquals(getAllLines
                        ("C:\\Users\\Admin\\IdeaProjects\\weatherStationServerr\\weatherStationServer" +
                                "\\src\\test\\resources\\expectedMaintenanceJob.csv"),
                getAllLines("C:\\Users\\Admin\\IdeaProjects\\weatherStationServerr\\files" +
                        "\\maintenanceJob-2022-06-22.csv"));
    }
}