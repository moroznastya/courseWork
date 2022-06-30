package ua.lviv.iot.weatherStationServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.lviv.iot.weatherStationServer.logic.MaintenanceJobService;
import ua.lviv.iot.weatherStationServer.model.MaintenanceJob;

import java.util.List;

@RestController
@RequestMapping("/maintenanceJob")
public class MaintenanceJobController {
    @Autowired
    private MaintenanceJobService maintenanceJobService;

    @GetMapping
    public List<MaintenanceJob> getMaintenanceJobs() {

        return maintenanceJobService.getMaintenanceJobs();
    }

    @GetMapping("/{weatherStationId}")
    public MaintenanceJob getMaintenanceJobByWeatherStationId(@PathVariable Long weatherStationId) {
        if(maintenanceJobService.getMaintenanceJobByWeatherStationId(weatherStationId) == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        } else {
            return maintenanceJobService.getMaintenanceJobByWeatherStationId(weatherStationId);
        }

    }

    @PostMapping
    public void addMaintenanceJob(@RequestBody MaintenanceJob maintenanceJob) {
        maintenanceJobService.addMaintenanceJob(maintenanceJob);
    }

    @PutMapping("/{weatherStationId}")
    public void updateMaintenanceJob(@RequestBody MaintenanceJob maintenanceJob,
                                     @PathVariable Long weatherStationId) {
        maintenanceJobService.updateMaintenanceJob(maintenanceJob, weatherStationId);
    }

    @DeleteMapping("/{weatherStationId}")
    public void deleteMaintenanceJob(@PathVariable Long weatherStationId) {
        maintenanceJobService.deleteMaintenanceJob(weatherStationId);
    }
}
