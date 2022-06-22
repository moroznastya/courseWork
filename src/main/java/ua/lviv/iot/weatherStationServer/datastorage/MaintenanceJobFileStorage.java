package ua.lviv.iot.weatherStationServer.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.weatherStationServer.model.MaintenanceJob;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Component
public class MaintenanceJobFileStorage {
    public List<MaintenanceJob> findMaintenanceJobInMonth() throws IOException {
        List<MaintenanceJob> result = new LinkedList<>();
        File file;

        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {

            if (Files.exists(Paths.get("files//" + "maintenanceJob-" + "-" + LocalDate.now() + ".csv"))) {
                file = new File("files//" + "maintenanceJob-" + LocalDate.now() + ".csv");
                result.addAll(scanMaintenanceJob(file));
            }

        }
        return result;
    }

    private List<MaintenanceJob> scanMaintenanceJob(File file) throws IOException {
        List<MaintenanceJob> result = new LinkedList<>();
        boolean isFirst = true;
        Scanner scanner = new Scanner(file, "UTF_8");
        while (scanner.hasNextLine()) {
            if (!isFirst) {
                List<String> values = Arrays.stream(scanner.nextLine().split(", ")).toList();
                result.add(fillMaintenanceJob(values));
            } else {
                scanner.nextLine();
                isFirst = false;
            }
        }
        return result;
    }


    public MaintenanceJob fillMaintenanceJob(List<String> values) {
        MaintenanceJob maintenanceJob = new MaintenanceJob();
        int index = 0;
        for (String value : values) {
            switch (index) {
                case 0 -> maintenanceJob.setWeatherStationId(Long.parseLong(value));

                case 1 -> maintenanceJob.setDataOfServiceWorks(value);

                case 2 -> maintenanceJob.setDescriptionOfServiceWorks(value);

                default -> { }
            }

            index++;

        }
        return maintenanceJob;

    }
    public void saveMaintenanceJob(List<MaintenanceJob> maintenanceJobs) throws IOException {


        File file = new File("files//" + "maintenanceJob-" + LocalDate.now() + ".csv");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(maintenanceJobs.get(0).getHeaders() + "\n");
        for (MaintenanceJob maintenanceJob: maintenanceJobs) {
            writer.write(maintenanceJob.toCSV() + "\n");
        }
        writer.close();

    }

}
