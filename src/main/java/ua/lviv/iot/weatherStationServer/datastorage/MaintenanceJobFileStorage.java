package ua.lviv.iot.weatherStationServer.datastorage;

import org.springframework.stereotype.Component;
import ua.lviv.iot.weatherStationServer.model.MaintenanceJob;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Component
public class MaintenanceJobFileStorage extends Storage {
    private final String path = getPath("maintenanceJob");

    public List<MaintenanceJob> findMaintenanceJobInMonth() throws IOException {
        List<MaintenanceJob> result = new LinkedList<>();
        File file;
        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {
            if (Files.exists(Paths.get(path))) {
                file = new File(path);
                result.addAll(scanMaintenanceJob(file));
            }
        }
        return result;
    }

    private List<MaintenanceJob> scanMaintenanceJob(File file) throws IOException {
        List<MaintenanceJob> result = new LinkedList<>();
        prepareFileForReading(file);
        String [] values;
        while (scanner.hasNextLine()) {
            values = getNextLineFromFile();
            if(values != null) {
                result.add(new MaintenanceJob
                        (Long.valueOf(values[0]), values[1], values[2]));
            }
        }
        return result;
    }


    public void saveMaintenanceJob(List<MaintenanceJob> maintenanceJobs) throws IOException {

        File file = new File(path);
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        writer.write(maintenanceJobs.get(0).getHeaders() + "\n");
        for (MaintenanceJob maintenanceJob: maintenanceJobs) {
            writer.write(maintenanceJob.toCSV() + "\n");
        }
        writer.close();

    }

}
