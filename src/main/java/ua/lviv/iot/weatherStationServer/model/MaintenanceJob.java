package ua.lviv.iot.weatherStationServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MaintenanceJob {
    private Long weatherStationId;
    private String dataOfServiceWorks;
    private String descriptionOfServiceWorks;

    public String getHeaders() {
        return "weatherStationId, " +
                "dataOfServiceWorks, " +
                "descriptionOfServiceWorks";
    }

    public String toCSV() {
        return weatherStationId + ", " + dataOfServiceWorks + ", " +
                descriptionOfServiceWorks + ", ";

    }
}
