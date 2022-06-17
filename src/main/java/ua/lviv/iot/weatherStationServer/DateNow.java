package ua.lviv.iot.weatherStationServer;

import java.time.LocalDate;

public class DateNow {

    public static String getTimeNow() {
        String year = Integer.toString(LocalDate.now().getYear());
        String month;
        String day;

        if (LocalDate.now().getMonthValue() < 10) {
            month = "0" + LocalDate.now().getMonthValue();
        } else {
            month = Integer.toString(LocalDate.now().getMonthValue());
        }
        if (LocalDate.now().getDayOfMonth() < 10) {
            day = "0" + LocalDate.now().getDayOfMonth();
        } else {
            day = Integer.toString(LocalDate.now().getDayOfMonth());
        }

        return year + "-" + month + "-" + day;
    }

}
