package org.geekhub.crypto.db;

import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.sql.*;
import java.util.Properties;

public class DataSource {

    private static final Logger logger = LoggerFactory.getLogger();

    static {
        createIfNotExist();
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/maksym_onoshko";
        Properties props = new Properties();
        props.setProperty("user", "prompt");
        props.setProperty("password", "1111");
        props.setProperty("ssl", "true");
        props.setProperty("sslmode", "verify-ca");
        props.setProperty("sslfactory", "org.postgresql.ssl.DefaultJavaSSLFactory");
        return DriverManager.getConnection(url, props);
    }

    private static void createIfNotExist() {
        String query = "select exists (" +
                "select 1 from information_schema.tables " +
                "where table_schema ='geekhub' AND table_name = 'history');";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            if (!rs.next()) {
                createTable();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private static void createTable() {
        String query = "CREATE TABLE geekhub.history(" +
                "id serial primary key," +
                "operation varchar(30)  not null," +
                "codec varchar(30)," +
                "user_input varchar(256)," +
                "date timestamp not null)";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
