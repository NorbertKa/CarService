package bean;

import java.io.Serializable;

public class Car implements Serializable {
    private Integer id = null;
    private String manufacturer = null;
    private String model = null;
    private String regNumber = null;

    public Car() {}

    public Car(Integer id, String manufacturer, String model, String regNumber) {
        if (id != null) {
            this.id = id;
        }
        if (manufacturer != null) {
            this.manufacturer = manufacturer;
        }
        if (model != null) {
            this.model = model;
        }
        if (regNumber != null) {
            this.regNumber = regNumber;
        }
    }

    public Car(String manufacturer, String model, String regNumber) {
        if (manufacturer != null) {
            this.manufacturer = manufacturer;
        }
        if (model != null) {
            this.model = model;
        }
        if (regNumber != null) {
            this.regNumber = regNumber;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id != null) {
            this.id = id;
        }
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer != null) {
            this.manufacturer = manufacturer;
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model != null) {
            this.model = model;
        }
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        if (regNumber != null) {
            this.regNumber = regNumber;
        }
    }

    public boolean checkId() {
        return id != null;
    }
}
