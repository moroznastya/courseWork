package ua.lviv.iot.weatherStationServer.logic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.weatherStationServer.datastorage.MaintenanceJobFileStorage;
import ua.lviv.iot.weatherStationServer.model.MaintenanceJob;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class MaintenanceJobService {


    HashMap<Long, MaintenanceJob> maintenanceJobs = new HashMap<>();


    @Autowired
    MaintenanceJobFileStorage maintenanceJobFileStorage =new MaintenanceJobFileStorage();


    public List<MaintenanceJob> getMaintenanceJobs() {

        return new LinkedList<>(this.maintenanceJobs.values());
    }

    public MaintenanceJob getMaintenanceJobByWeatherStationId(Long weatherStationId) {

        return maintenanceJobs.get(weatherStationId);
    }

    public void addMaintenanceJob(MaintenanceJob maintenanceJob) {
        maintenanceJobs.put(maintenanceJob.getWeatherStationId(), maintenanceJob);
    }

    public void updateMaintenanceJob(MaintenanceJob maintenanceJob, Long weatherStationId) {
        MaintenanceJob oldMaintenanceJob = this.maintenanceJobs.get(weatherStationId);
        MaintenanceJob newMaintenanceJob = new MaintenanceJob();

        this.maintenanceJobs.remove(weatherStationId);

        newMaintenanceJob.setWeatherStationId(oldMaintenanceJob.getWeatherStationId());

        if (maintenanceJob.getDataOfServiceWorks() != null) {
            newMaintenanceJob.setDataOfServiceWorks(oldMaintenanceJob.getDataOfServiceWorks());
        } else {
            newMaintenanceJob.setDataOfServiceWorks(oldMaintenanceJob.getDataOfServiceWorks());
        }

        if (maintenanceJob.getDescriptionOfServiceWorks() != null) {
            newMaintenanceJob.setDescriptionOfServiceWorks(oldMaintenanceJob.getDescriptionOfServiceWorks());
        } else {
            newMaintenanceJob.setDescriptionOfServiceWorks(oldMaintenanceJob.getDescriptionOfServiceWorks());
        }


        this.maintenanceJobs.put(newMaintenanceJob.getWeatherStationId(), newMaintenanceJob);
    }

    public void deleteMaintenanceJob(Long weatherStationId) {

        this.maintenanceJobs.remove(weatherStationId);
    }



    @PreDestroy
    private void saveMaintenanceJobs() throws IOException {
        List<MaintenanceJob> list = this.maintenanceJobs.values().stream().toList();
        maintenanceJobFileStorage.saveMaintenanceJob(list);
    }

    @PostConstruct
    private void fillMaintenanceJobs() throws IOException {
        if (maintenanceJobFileStorage.findMaintenanceJobInMonth() != null) {
            List<MaintenanceJob> list = maintenanceJobFileStorage.findMaintenanceJobInMonth();
            for (MaintenanceJob maintenanceJob: list) {
                this.maintenanceJobs.put(maintenanceJob.getWeatherStationId(), maintenanceJob);
            }
        }
    }
}
