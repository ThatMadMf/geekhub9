package org.geekhub.crypto.history;

import org.geekhub.crypto.coders.Algorithm;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RecordRowMapper implements RowMapper<HistoryRecord> {

    @Override
    public HistoryRecord mapRow(ResultSet set, int rowNum) throws SQLException {
        Operation operation = Operation.valueOf(set.getString("operation"));
        String codec = set.getString("codec");
        Algorithm algorithm = codec == null ? null : Algorithm.valueOf(codec);
        String userInput = set.getString("user_input");
        LocalDate date = set.getDate("operation_date").toLocalDate();

        return new HistoryRecord(operation, userInput, algorithm, date);
    }
}
