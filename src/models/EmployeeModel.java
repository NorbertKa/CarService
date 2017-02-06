package models;

import bean.Employee;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    private static DataSource dataSource;
    private static HikariConfig hikariConfig;

    public static void setDataSource(DataSource data) {
        if (dataSource == null) {
            if (data != null) {
                dataSource = data;
            }
        }
    }

    public static void setDataSource(HikariConfig conf) {
        if (dataSource == null) {
            if (conf != null) {
                hikariConfig = conf;
                dataSource = new HikariDataSource(hikariConfig);
            }
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public EmployeeModel() {}

    public Employee getEmployee(Integer id) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Employee employee = new Employee();
        try {
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement("SELECT ID, Name, Surname FROM Employee WHERE ID = ?");

            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                employee.setId(resultSet.getInt(1));
                employee.setName(resultSet.getString(2));
                employee.setSurname(resultSet.getString(3));
            }
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return employee;
    }

    public List<Employee> getEmployees(Integer fromWhen, String name) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Employee> employeeList = new ArrayList<Employee>();
        String SQL = "Select ID, Name, Surname FROM Employee";

        if (name != null) {
            SQL += " WHERE Name LIKE '" + name + "%'";
        }

        if (fromWhen != null) {
            fromWhen *= 10;
            Integer offset = fromWhen - 10;
            SQL += " ORDER BY ID ASC LIMIT " + fromWhen.toString() + " OFFSET " + offset.toString();
        } else {
            SQL += " ORDER BY ID ASC";
        }

        try {
            connection = dataSource.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setSurname(rs.getString(3));
                employeeList.add(employee);
            }
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                connection.close();
                rs.close();
            }
        }
        return employeeList;
    }

    public void insertEmployee(Employee employee) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String SQL = "INSERT INTO Employee(Name, Surname) VALUES (?, ?)";
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
