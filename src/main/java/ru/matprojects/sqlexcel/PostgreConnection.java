package ru.matprojects.sqlexcel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgreConnection {
    private final Logger log = LoggerFactory.getLogger(PostgreConnection.class);
    private final String DB_URL;
    private final String USER;
    private final String PASS;

    private List<String> executeSqls;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private void setConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            log.error("Connection failed");
        }
    }

    public List<String> getExecuteSqls() {
        return executeSqls;
    }

    public void setExecuteSqls(List<String> executeSqls) {
        this.executeSqls = executeSqls;
    }

    public PostgreConnection() {
        JsonConfigReader jsReader = new JsonConfigReader();
        Map<String, Object> pars = jsReader.readJdbcPar();
        DB_URL = pars.getOrDefault("connection_string", "").toString();
        USER = pars.getOrDefault("username", "").toString();
        PASS = pars.getOrDefault("password", "").toString();

        setExecuteSqls((List<String>) pars.getOrDefault("sqlList", new ArrayList<String>()));
        setConnection();
    }

    public void closeConnection() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            log.error("Error close connection");
        }
    }
}
