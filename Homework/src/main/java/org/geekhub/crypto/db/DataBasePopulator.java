package org.geekhub.crypto.db;

import org.geekhub.crypto.logging.CompositeLogger;
import org.geekhub.crypto.logging.Logger;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DataBasePopulator {

    private final Logger logger;

    public DataBasePopulator(CompositeLogger logger) {
        this.logger = logger;
    }

    public void createTable(DataPool dataSource) {
        String schemaQuery = "create schema geekhub";
        String tableQuery = "CREATE TABLE geekhub.history(" +
                "id serial primary key," +
                "operation varchar(30)  not null," +
                "codec varchar(30)," +
                "user_input varchar(256)," +
                "operation_date timestamp not null)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement schema = connection.prepareStatement(schemaQuery);
             PreparedStatement table = connection.prepareStatement(tableQuery)) {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet schemaExist = meta.getSchemas(null, "geekhub");
            ResultSet tableExist = meta.getTables(null, "geekhub", "history", null);
            if (!schemaExist.next()) {
                schema.executeUpdate();
                table.executeUpdate();
            } else if (!tableExist.next()) {
                table.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(e.getMessage());
        }
    }
}
