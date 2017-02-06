package bean;

public class Timeframe {
    private Integer id = null;
    private String day = null;
    private String timeFrom = null;
    private String timeTo = null;
    private Integer carId = null;
    private Integer employeeCarId = null;

    public Timeframe() {}

    public Timeframe(String day, String timeFrom, String timeTo, Integer carId, Integer employeeCarId) {
        if(day != null) {
            this.day = day;
        }
        if(timeFrom != null) {
            this.timeFrom = timeFrom;
        }
        if(timeTo != null) {
            this.timeTo = timeTo;
        }
        if(carId != null) {
            this.carId = carId;
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

    public void setTimeFrom(String timeFrom) {
        if(timeFrom != null) {
            this.timeFrom = timeFrom;
        }
    }

    public String getTimeTo() { return timeTo; }

    public void setTimeTo(String timeTo) {
        if(timeTo != null) {
            this.timeTo = timeTo;
        }
    }

    public Integer getCarId() { return carId; }

    public void setCarId(Integer carId) {
        if(carId != null) {
            this.carId = carId;
        }
    }

    public Integer getEmployeeCarId() { return employeeCarId; }

    public void setEmployeeCarId(Integer employeeCarId) {
        if(employeeCarId != null) {
            this.employeeCarId = employeeCarId;
        }
    }
}
