package org.geekhub.crypto.analytics;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;

import java.time.LocalDate;
import java.util.*;

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
        Map<String, Integer> result = new TreeMap<>(Collections.reverseOrder());

        for (HistoryRecord record : encodeOperations) {
            String[] words = record.getUserInput().split(" ");
            result = checkWords(result, words);
        }
        return result;
    }

    public Map<LocalDate, Long> countCodingsByDate(CodecUsecase usecase) {
        List<HistoryRecord> operations = codingHistory.getHistoryRecords(usecase);
        Map<LocalDate, Long> result = new HashMap<>();

        for (HistoryRecord record : operations) {
            LocalDate date = record.getOperationDate();
            if (result.containsKey(date)) {
                result.put(date, result.get(date) + 1);
            } else {
                result.put(date, 1L);
            }
        }
        return result;
    }

    public Algorithm findMostPopularCodec(CodecUsecase usecase) {
        List<HistoryRecord> operations = codingHistory.getHistoryRecords(usecase);
        Map<Algorithm, Integer> result = new TreeMap<>();

        for (HistoryRecord record : operations) {
            Algorithm algorithm = record.getCodec();
            if (result.containsKey(algorithm)) {
                result.put(algorithm, result.get(algorithm) + 1);
            } else {
                result.put(algorithm, 1);
            }
        }

        if (result.isEmpty()) {
            return null;
        } else {
            Map.Entry<Algorithm, Integer> entry = result.entrySet().iterator().next();
            return entry.getKey();
        }
    }

    private Map<String, Integer> checkWords(Map<String, Integer> input, String[] words) {
        Map<String, Integer> result = input;
        for (String word : words) {
            if (result.containsKey(word)) {
                result.put(word, result.get(word) + 1);
            } else {
                result.put(word, 1);
            }
        }
        return result;
    }
}
