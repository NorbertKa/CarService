package bean;

import java.io.Serializable;

public class Employee implements Serializable {
    private Integer id = null;
    private String name = null;
    private String surname = null;

    public Employee() {}

    public Employee(Integer id, String name, String surname) {
        if (id != null) {
            this.id = id;
        }
        if (name != null) {
            this.name = name;
        }
        if (surname != null) {
            this.surname = surname;
        }
    }

    public Employee(String name, String surname) {
        if (name != null) {
            this.name = name;
        }
        if (surname != null) {
            this.surname = surname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname != null) {
            this.surname = surname;
        }
    }

    public boolean checkId() {
        return id != null;
    }
}
