package org.geekhub.crypto.analytics;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.util.EmptyHistoryException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CodingAudit {
    private final CodingHistory codingHistory;

    public CodingHistory getCodingHistory() {
        return codingHistory;
    }

    public CodingAudit(CodingHistory codingHistory) {
        this.codingHistory = codingHistory;
    }

    public Map<String, Integer> countEncodingInputs() {
        return codingHistory.getHistoryRecords(CodecUsecase.ENCODING)
                .stream()
                .collect(Collectors.groupingBy(
                        HistoryRecord::getUserInput,
                        () -> new TreeMap<>(Collections.reverseOrder()),
                        Collectors.summingInt(e -> 1)
                ));
    }

    public Map<LocalDate, Long> countCodingsByDate(CodecUsecase usecase) {
        return codingHistory.getHistoryRecords(usecase)
                .stream()
                .collect(Collectors.groupingBy(
                        HistoryRecord::getOperationDate,
                        () -> new TreeMap<>(Collections.reverseOrder()),
                        Collectors.summingLong(e -> 1)
                ));
    }

    public Algorithm findMostPopularCodec(CodecUsecase usecase) {
        return codingHistory.getHistoryRecords(usecase)
                .stream()
                .collect(Collectors.groupingBy(HistoryRecord::getCodec, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new EmptyHistoryException("History is empty"));
    }
}
