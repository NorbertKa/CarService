package models;

import bean.EmployeeCar;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCarModel {
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

    public EmployeeCarModel() {}

    public EmployeeCar getEmployeeCar(Integer id) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        EmployeeCar employeeCar = new EmployeeCar();
        try {
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement("SELECT ID, EmployeeID, CarID FROM EmployeeCar WHERE ID = ?");

            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                employeeCar.setId(resultSet.getInt(1));
                employeeCar.setEmployeeId(resultSet.getInt(2));
                employeeCar.setCarId(resultSet.getInt(3));
            }
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return employeeCar;
    }

    public List<EmployeeCar> getEmployeeCars(Integer fromWhen, Integer employeeId, Integer carId) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<EmployeeCar> employeeCarList = new ArrayList<EmployeeCar>();
        String SQL = "SELECT ID, EmployeeID, CarID FROM EmployeeCar";

        if (employeeId != null && carId != null) {
            SQL += " WHERE EmployeeID = " + employeeId.toString() + " AND CarID = " + carId.toString();
        } else {
            if (employeeId != null) {
                SQL += " WHERE EmployeeID = " + employeeId.toString();
            } else if (carId != null) {
                SQL += " WHERE CarID = " + carId.toString();
            }
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
                EmployeeCar employeeCar = new EmployeeCar();
                employeeCar.setId(rs.getInt(1));
                employeeCar.setEmployeeId(rs.getInt(2));
                employeeCar.setCarId(rs.getInt(3));
                employeeCarList.add(employeeCar);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return employeeCarList;
    }

    public void insertEmployeeCar(EmployeeCar employeeCar) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String SQL = "INSERT INTO EmployeeCar(EmployeeID, CarID) VALUES (?, ?)";
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, employeeCar.getEmployeeId());
            preparedStatement.setInt(2, employeeCar.getCarId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
