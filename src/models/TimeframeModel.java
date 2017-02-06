package models;

import bean.Timeframe;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeframeModel {
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

    public TimeframeModel() {}

    public Timeframe getEmployeeCar(Integer id) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Timeframe timeframe = new Timeframe();
        try {
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement("SELECT ID, Day, TimeFrom, TimeTo, CarID, EmployeeID FROM Timeframe WHERE ID = ?");

            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                timeframe.setId(resultSet.getInt(1));
                timeframe.setTimeFrom(resultSet.getString(2));
                timeframe.setTimeTo(resultSet.getString(3));
                timeframe.setCarId(resultSet.getInt(4));
                timeframe.setEmployeeCarId(resultSet.getInt(5));
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
        return timeframe;
    }

    public List<Timeframe> getEmployeeCars(Integer fromWhen, Integer carId, Integer employeeCarId) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Timeframe> timeframeList = new ArrayList<Timeframe>();
        String SQL = "SELECT ID, Day, TimeFrom, TimeTo, CarID, EmployeeID FROM Timeframe";

        if (carId != null && employeeCarId != null) {
            SQL += " WHERE CarID = " + carId.toString() + " AND EmployeeCarID = " + employeeCarId.toString();
        } else {
            if (carId != null) {
                SQL += " WHERE CarID = " + carId.toString();
            } else if (employeeCarId != null) {
                SQL += " WHERE EmployeeCarID = " + employeeCarId.toString();
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
                Timeframe timeframe = new Timeframe();
                timeframe.setId(rs.getInt(1));
                timeframe.setTimeFrom(rs.getString(2));
                timeframe.setTimeTo(rs.getString(3));
                timeframe.setCarId(rs.getInt(4));
                timeframe.setEmployeeCarId(rs.getInt(5));
                timeframeList.add(timeframe);
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
        return timeframeList;
    }

    public void insertTimeframe(Timeframe timeframe) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String SQL = "INSERT INTO Timeframe(Day, TimeFrom, TimeTo, CarID, EmployeeCarID) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, timeframe.getDay());
            preparedStatement.setTime(2, Time.valueOf(LocalTime.parse(timeframe.getTimeFrom())));
            preparedStatement.setTime(3, Time.valueOf(LocalTime.parse(timeframe.getTimeTo())));
            preparedStatement.setInt(4, timeframe.getCarId());
            preparedStatement.setInt(5, timeframe.getEmployeeCarId());
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
