package models;

import bean.Car;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarModel {
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

    public CarModel() {}

    public Car getCar(Integer id) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Car car = new Car();
        try {
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement("SELECT ID, Manufacturer, Model, RegNumber FROM Car WHERE ID = ?");

            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                car.setId(resultSet.getInt(1));
                car.setManufacturer(resultSet.getString(2));
                car.setModel(resultSet.getString(3));
                car.setRegNumber(resultSet.getString(4));
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
        return car;
    }

    public List<Car> getCars(Integer fromWhen, String regNumber, String manufacturer) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Car> carList = new ArrayList<Car>();
        String SQL = "Select ID, Manufacturer, Model, RegNumber FROM Car";

        if (regNumber != null && manufacturer != null) {
            SQL += " WHERE RegNumber LIKE '" + regNumber + "%' AND Manufacturer LIKE '" + manufacturer + "%'";
        } else {
            if (regNumber != null) {
                SQL += " WHERE RegNumber LIKE '" + regNumber + "%'";
            } else if (manufacturer != null) {
               SQL += " WHERE Manufacturer LIKE '" + manufacturer + "%'";
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
            System.out.println(SQL);
            rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt(1));
                car.setManufacturer(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setRegNumber(rs.getString(4));
                carList.add(car);
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
        return carList;
    }

    public void insertCar(Car car) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String SQL = "INSERT INTO Car(Manufacturer, Model, RegNumber) VALUES (?, ?, ?)";
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, car.getManufacturer());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getRegNumber());
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
