package org.geekhub.crypto.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataSource {

    private static final Logger logger = LoggerFactory.getLogger();
    private static final HikariConfig config = initialiseHikari();
    private static final HikariDataSource hikariDataSource = new HikariDataSource(config);

    static {
        createTable();
    }

    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private static HikariConfig initialiseHikari() {
        Properties properties = new Properties();
        try (InputStream stream = DataSource.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (stream != null) {
                properties.load(stream);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return new HikariConfig(properties);
    }

    private static void createTable() {
        String schemaQuery = "create schema geekhub";
        String tableQuery = "CREATE TABLE geekhub.history(" +
                "id serial primary key," +
                "operation varchar(30)  not null," +
                "codec varchar(30)," +
                "user_input varchar(256)," +
                "date timestamp not null)";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement schema = connection.prepareStatement(schemaQuery);
             PreparedStatement table = connection.prepareStatement(tableQuery)) {
            schema.executeUpdate();
            table.executeUpdate();
        } catch (SQLException e) {
            logger.log(e.getMessage());
        }
    }
}
