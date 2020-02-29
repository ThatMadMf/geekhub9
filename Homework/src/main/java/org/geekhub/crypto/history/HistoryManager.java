package org.geekhub.crypto.history;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.db.RecordRowMapper;
import org.geekhub.crypto.logging.CompositeLogger;
import org.geekhub.crypto.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class HistoryManager {
    private final Logger logger;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public HistoryManager(JdbcTemplate jdbcTemplate, CompositeLogger logger, DataSource dataSource) {
        this.logger = logger;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public void addToHistory(HistoryRecord record) {
        String query = "insert into geekhub.history (operation, codec, user_input, operation_date)" +
                "values (?, ?, ?, ?)";
        String codec = record.getCodec() == null ? null : record.getCodec().name();
        jdbcTemplate.update(query, record.getOperation().name(),
                codec, record.getUserInput(), record.getOperationDate());
    }

    public List<HistoryRecord> readHistory() {
        String query = "select operation, codec, user_input, operation_date from geekhub.history " +
                "order by id, operation_date desc";
        return jdbcTemplate.query(query, new RecordRowMapper());
    }

    public void removeLastRecord() {
        jdbcTemplate.update("delete from geekhub.history " +
                "where id in(select id from geekhub.history order by id desc limit 1)");
    }

    public void clearHistory() {
        jdbcTemplate.update("delete from geekhub.history");
    }
}
