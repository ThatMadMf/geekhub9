package org.geekhub.crypto.history;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.db.DataSource;
import org.geekhub.crypto.logging.CompositeLogger;
import org.geekhub.crypto.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component
public class HistoryManager {
    private final Logger logger;
    private final DataSource dataSource;

    @Autowired
    public HistoryManager(DataSource source, CompositeLogger logger) {
        this.logger = logger;
        dataSource = source;
    }

    public void addToHistory(HistoryRecord record) {
        String query = "insert into geekhub.history (operation, codec, user_input, operation_date)" +
                "values (?, ?, ?, ?)";
        String codec = record.getCodec() == null ? null : record.getCodec().name();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, record.getOperation().name());
            preparedStatement.setString(2, codec);
            preparedStatement.setString(3, record.getUserInput());
            preparedStatement.setDate(4, Date.valueOf(record.getOperationDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public List<HistoryRecord> readHistory() {
        List<HistoryRecord> records = new LinkedList<>();
        String query = "select operation, codec, user_input, operation_date from geekhub.history " +
                "order by id, operation_date desc";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String codec = resultSet.getString("codec");
                Algorithm algorithm = codec == null ? null : Algorithm.valueOf(codec);
                HistoryRecord record = new HistoryRecord(
                        Operation.valueOf(resultSet.getString("operation")),
                        resultSet.getString("user_input"),
                        algorithm,
                        resultSet.getDate("operation_date").toLocalDate()
                );
                records.add(record);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return records;
    }

    public void removeLastRecord() {
        String query = "delete from geekhub.history " +
                "where id in(select id from geekhub.history order by id desc limit 1)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void clearHistory() {
        String query = "delete from geekhub.history";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
