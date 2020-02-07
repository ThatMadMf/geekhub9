package org.geekhub.crypto.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.geekhub.crypto.logging.CompositeLogger;
import org.geekhub.crypto.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DataSource {

    private Logger logger;
    private HikariDataSource hikariDataSource = new HikariDataSource(initialiseHikari());

    @Autowired
    public DataSource(CompositeLogger logger) {
        this.logger = logger;
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private HikariConfig initialiseHikari() {
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
}
