package models;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class BaseModel {
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

    public static HikariConfig getHikariConfig() {
        return hikariConfig;
    }
}

