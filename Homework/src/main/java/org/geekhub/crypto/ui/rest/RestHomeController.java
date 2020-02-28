package org.geekhub.crypto.ui.rest;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.DecoderFactory;
import org.geekhub.crypto.coders.EncoderFactory;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RestHomeController {

    private DecoderFactory decoderFactory;
    private EncoderFactory encoderFactory;
    private HistoryManager historyManager;
    private CodingAudit audit;

    public RestHomeController(DecoderFactory decoderFactory, EncoderFactory
            encoderFactory, HistoryManager historyManager, CodingAudit audit) {
        this.decoderFactory = decoderFactory;
        this.encoderFactory = encoderFactory;
        this.historyManager = historyManager;
        this.audit = audit;
    }

    @GetMapping("/api/decode")
    public String decode(@RequestParam Algorithm algorithm, String text) {
        HistoryRecord record = new HistoryRecord(Operation.DECODE, text, algorithm);
        historyManager.addToHistory(record);
        return decoderFactory.getDecoder(algorithm).decode(text);
    }

    @GetMapping("/api/encode")
    public String encode(@RequestParam Algorithm algorithm, String text) {
        HistoryRecord record = new HistoryRecord(Operation.ENCODE, text, algorithm);
        historyManager.addToHistory(record);
        return encoderFactory.getEncoder(algorithm).encode(text);
    }

    @GetMapping("api/history/show-history")
    public List<String> showHistory() {
        return historyManager.readHistory().stream()
                .map(HistoryRecord::getStringRepresentation)
                .collect(Collectors.toList());
    }

    @DeleteMapping("api/history/remove-last")
    public void removeLastPost() {
        historyManager.removeLastRecord();
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("api/history/clear-history")
    public void clearHistory() {
        historyManager.clearHistory();
    }

    @GetMapping("api/analytics/count-by-date")
    public Map<LocalDate, Long> countByDate(@RequestParam CodecUsecase usecase) {
        historyManager.addToHistory(new HistoryRecord(Operation.ANALYTICS));
        return audit.countCodingsByDate(usecase);
    }

    @GetMapping("api/analytics/count-inputs")
    public Map<String, Integer> countInputs() {
        historyManager.addToHistory(new HistoryRecord(Operation.ANALYTICS));
        return audit.countEncodingInputs();
    }

    @GetMapping("api/analytics/find-most-popular-codec")
    public Algorithm findMostPopular(@RequestParam CodecUsecase usecase) {
        historyManager.addToHistory(new HistoryRecord(Operation.ANALYTICS));
        return audit.findMostPopularCodec(usecase);
    }
}
