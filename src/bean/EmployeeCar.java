package bean;

public class EmployeeCar {
    private Integer id = null;
    private Integer employeeId = null;
    private Integer carId = null;

    public EmployeeCar() {}

    public EmployeeCar(Integer employeeId, Integer carId) {
        if(employeeId != null) {
            this.employeeId = employeeId;
        }
        if(carId != null) {
            this.carId = carId;
        }
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        if(id != null) {
            this.id = id;
        }
    }

    public Integer getEmployeeId() { return employeeId; }

    public void setEmployeeId(Integer employeeId) {
        if(employeeId != null) {
            this.employeeId = employeeId;
        }
    }

    public Integer getCarId() { return carId; }

    public void setCarId(Integer carId) {
        if(carId != null) {
            this.carId = carId;
        }
    }

    public boolean checkId() { return id != null; }
}