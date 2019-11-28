package org.geekhub.crypto.analytics;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodingAudit {
    private final CodingHistory codingHistory;

    public CodingHistory getCodingHistory() {
        return codingHistory;
    }

    public CodingAudit(CodingHistory codingHistory) {
        this.codingHistory = codingHistory;
    }

    public Map<String, Integer> countEncodingInputs() {
        List<HistoryRecord> encodeOperations = codingHistory.getHistoryRecords(CodecUsecase.ENCODING);
        List<String> words = groupBy(encodeOperations, HistoryRecord::getUserInput);

        return processCollectionInt(words);
    }

    public Map<LocalDate, Long> countCodingsByDate(CodecUsecase usecase) {
        List<HistoryRecord> operations = codingHistory.getHistoryRecords(usecase);

        List<LocalDate> dates = groupBy(operations, HistoryRecord::getOperationDate);
        return processCollectionLong(dates);
    }

    public Algorithm findMostPopularCodec(CodecUsecase usecase) {
        List<HistoryRecord> operations = codingHistory.getHistoryRecords(usecase);
        List<Algorithm> algorithmList = groupBy(operations, HistoryRecord::getCodec);
        Map<Algorithm, Integer> result = processCollectionInt(algorithmList);

        if (result.isEmpty()) {
            return null;
        } else {
            Map.Entry<Algorithm, Integer> entry = result.entrySet().iterator().next();
            return entry.getKey();
        }
    }

    private  <T> List<T> groupBy(List<HistoryRecord> records, Function<HistoryRecord, T> filter) {
        return records.stream().map(
                record -> Stream.of(filter.apply(record))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private <K>  Map <K, Integer> processCollectionInt(List<K> items) {
        Map<K, Integer> result = new TreeMap<>(Collections.reverseOrder());

        items.stream().forEach(item -> {
            if (result.containsKey(item)) {
                result.put(item, result.get(item) + 1);
            } else {
                result.put(item, 1);
            }
        });
        return result;
    }

    private <K>  Map <K, Long> processCollectionLong(List<K> items) {
        Map<K, Long> result = new TreeMap<>();

        items.stream().forEach(item -> {
            if (result.containsKey(item)) {
                result.put(item, result.get(item).intValue() + 1L);
            } else {
                result.put(item, 1L);
            }
        });
        return result;
    }
}
