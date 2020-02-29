package org.geekhub.crypto.db;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataPool {

    private DataSource dataSourceSource;

    public Connection getConnection() throws SQLException {
        return dataSourceSource.getConnection();
    }

    public DataPool(DataSource dataSource) {
        this.dataSourceSource = dataSource;
    }

}
