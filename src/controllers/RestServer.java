package controllers;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.FlywayException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.CarModel;
import models.EmployeeCarModel;
import models.EmployeeModel;
import models.TimeframeModel;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.sql.DataSource;


public class RestServer {
    public static void main(String[] args) throws Exception {
        String dbHost = args[0];
        Integer dbPort = 5433;
        try {
            dbPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String dbName = args[2];
        String dbUsername = args[3];
        String dbPassword = args[4];

        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:postgresql://" + dbHost + ":" + dbPort.toString() + "/" + dbName, dbUsername, dbPassword);

        try {
            flyway.migrate();
        } catch (FlywayException e) {
            e.printStackTrace();
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://" + dbHost + ":" + dbPort.toString() + "/" + dbName);
        hikariConfig.setUsername(dbUsername);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setMaximumPoolSize(3);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 256);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 4096);

        DataSource dataSource = new HikariDataSource(hikariConfig);

        EmployeeModel.setDataSource(dataSource);
        CarModel.setDataSource(dataSource);
        EmployeeCarModel.setDataSource(dataSource);
        TimeframeModel.setDataSource(dataSource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(1337);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames", CarController.class.getCanonicalName()+
                        "," + EmployeeController.class.getCanonicalName()+
                        "," + EmployeeCarController.class.getCanonicalName()+
                        "," + TimeframeController.class.getCanonicalName());


        /*
        HandlerCollection handlers = new HandlerCollection();
        ContextHandlerCollection contexts = new ContextHandlerCollection();

        jettyServer.setHandler(handlers);
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename("./logs/yyyy_mm_dd.request.log");
        requestLog.setFilenameDateFormat("yyyy_MM_dd");
        requestLog.setRetainDays(7);
        requestLog.setAppend(true);
        requestLog.setExtended(true);
        requestLog.setLogCookies(true);
        requestLog.setLogLatency(true);
        requestLog.setLogTimeZone("GMT");
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(requestLog);
        handlers.addHandler(requestLogHandler);
        */

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
