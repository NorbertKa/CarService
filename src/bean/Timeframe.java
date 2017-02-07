package bean;

import java.sql.Struct;
import java.sql.Time;

public class Timeframe {
    private Integer id = null;
    private String day = null;
    private String timeFrom = null;
    private String timeTo = null;
    private Integer employeeCarId = null;

    public Timeframe() {}

    public Timeframe(String day, Time timeFrom, Time timeTo, Integer employeeCarId) {
        if(day != null) {
            this.day = day;
        }
        if(timeFrom != null) {
            this.timeFrom = String.valueOf(timeFrom);
        }
        if(timeTo != null) {
            this.timeTo = String.valueOf(timeTo);
        }
        if(employeeCarId != null) {
            this.employeeCarId = employeeCarId;
        }
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        if(id != null) {
            this.id = id;
        }
    }

    public String getDay() { return day; }

    public void setDay(String day) {
        if(day != null) {
            this.day = day;
        }
    }

    public String getTimeFrom() { return timeFrom; }

    public void setTimeFrom(Time timeFrom) {
        if(timeFrom != null) {
            this.timeFrom = String.valueOf(timeFrom);
        }
    }

    public String getTimeTo() { return timeTo; }

    public void setTimeTo(Time timeTo) {
        if(timeTo != null) {
            this.timeTo = String.valueOf(timeTo);
        }
    }


    public Integer getEmployeeCarId() { return employeeCarId; }

    public void setEmployeeCarId(Integer employeeCarId) {
        if(employeeCarId != null) {
            this.employeeCarId = employeeCarId;
        }
    }

    public boolean checkId() {
        return id != null;
    }
}
