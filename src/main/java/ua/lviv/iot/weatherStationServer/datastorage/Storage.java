package ua.lviv.iot.weatherStationServer.datastorage;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;


public class Storage {
    protected Scanner scanner;

    public void prepareFileForReading(File file) throws IOException {
        scanner = new Scanner(file);

        if (scanner.hasNextLine()) {
            scanner.nextLine();

        }
    }

    public String[] getNextLineFromFile()  {
        String [] values = null;
        while (scanner.hasNextLine()) {
            values = scanner.nextLine().split(", ");
        }
        return values;

    }

    public String getPath(String className){
        return "files//" + className + "-" + LocalDate.now() + ".csv";
    }



}
