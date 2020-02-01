package org.geekhub.crypto.ui.web.controller;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
public class AnalyticsController {

    private CodingAudit audit;
    private HistoryManager history;

    @Autowired
    public AnalyticsController(CodingAudit audit, HistoryManager history) {
        this.audit = audit;
        this.history = history;
    }

    @GetMapping("application/analytics/count-by-date")
    public String countByDate() {
        String response = "";
        response += "<form action = \"\" method = \"POST\">" +
                "Enter codec use case: <select name = \"usecase\">";
        for (CodecUsecase usecase : CodecUsecase.values()) {
            response += "<option value=\"" + usecase.name() + "\">" + usecase.name() + "</option><br>";
        }
        response += "</select>" + "<br>" +
                "<input type = \"submit\" value = \"Submit\"/>" +
                "</form>";
        return response;
    }

    @PostMapping("/application/analytics/count-by-date")
    public String countByDatePost(@RequestParam String usecase) {
        StringBuilder response = new StringBuilder();
        CodecUsecase codecUsecase = CodecUsecase.valueOf(usecase);

        Map<LocalDate, Long> res = audit.countCodingsByDate(codecUsecase);
        for (Map.Entry<LocalDate, Long> entry : res.entrySet()) {
            response.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
        history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));

        return response.toString();
    }

    @GetMapping("application/analytics/count-inputs")
    public String countInputs() {
        StringBuilder result = new StringBuilder();
        Map<String, Integer> res = audit.countEncodingInputs();
        for (Map.Entry<String, Integer> entry : res.entrySet()) {
            result.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
        history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));
        return result.toString();
    }

    @GetMapping("application/analytics/find-most-popular-codec")
    public String findMostPopular() {
        StringBuilder response = new StringBuilder();
        response.append("<form action = \"\" method = \"POST\">" +
                "Enter codec use case: <select name = \"usecase\">");

        for (CodecUsecase usecase : CodecUsecase.values()) {
            response.append(" <option value=\"" + usecase.name() + "\">" + usecase.name() + "</option><br>");
        }
        response.append("</select>" + "<br>" +
                "<input type = \"submit\" value = \"Submit\"/>" +
                "</form>");

        history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));

        return response.toString();
    }

    @PostMapping("application/analytics/find-most-popular-codec")
    public String findMostPopular(@RequestParam String usecase) {
        CodecUsecase codecUsecase = CodecUsecase.valueOf(usecase);

        Algorithm res = audit.findMostPopularCodec(codecUsecase);
        history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));
        return res.name();
    }
}
