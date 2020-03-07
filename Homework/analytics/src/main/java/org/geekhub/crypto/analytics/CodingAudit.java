package org.geekhub.crypto.analytics;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class CodingAudit {
    private final List<HistoryRecord> codingHistory;

    public CodingAudit(List<HistoryRecord> records) {
        codingHistory = records;
    }

    public Map<String, Integer> countEncodingInputs() {
        return codingHistory.stream()
                .filter(record -> record.getOperation().equals(Operation.ENCODE))
                .collect(Collectors.groupingBy(
                        HistoryRecord::getUserInput,
                        () -> new TreeMap<>(Collections.reverseOrder()),
                        Collectors.summingInt(e -> 1)
                ));
    }

    public Map<LocalDate, Long> countCodingsByDate(CodecUsecase usecase) {
        return codingHistory.stream()
                .filter(record -> record.getOperation().equals(getOperation(usecase)))
                .collect(Collectors.groupingBy(
                        HistoryRecord::getOperationDate,
                        () -> new TreeMap<>(Collections.reverseOrder()),
                        Collectors.summingLong(e -> 1)
                ));
    }

    public Algorithm findMostPopularCodec(CodecUsecase usecase) {
        return codingHistory.stream()
                .filter(record -> record.getOperation().equals(getOperation(usecase)))
                .collect(Collectors.groupingBy(HistoryRecord::getCodec, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new EmptyHistoryException("History is empty"));
    }

    private Operation getOperation(CodecUsecase usecase) {
        if (usecase.equals(CodecUsecase.ENCODING)) {
            return Operation.ENCODE;
        }

        if (usecase.equals(CodecUsecase.DECODING)) {
            return Operation.DECODE;
        }

        throw new IllegalArgumentException("Unsupported usecase");
    }
}
