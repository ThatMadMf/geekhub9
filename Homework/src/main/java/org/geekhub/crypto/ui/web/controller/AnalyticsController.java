package org.geekhub.crypto.ui.web.controller;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AnalyticsController {

    private CodingAudit audit;
    private HistoryManager history;

    @Autowired
    public AnalyticsController(CodingAudit audit, HistoryManager history) {
        this.audit = audit;
        this.history = history;
    }

    @GetMapping("application/analytics/count-by-date")
    public String countByDate(Model model) {
        model.addAttribute("operation", "count-by-date");
        model.addAttribute("usecases", CodecUsecase.values());
        return "data/analyticsView";
    }

    @PostMapping("/application/analytics/count-by-date")
    public String countByDatePost(@RequestParam String usecase, Model model) {
        List<String> response = audit.countCodingsByDate(CodecUsecase.valueOf(usecase)).entrySet().stream()
                .map(e -> e.getKey() + "  " + e.getValue())
                .collect(Collectors.toList());
        history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));
        model.addAttribute("title", "Count of inputs by date");
        model.addAttribute("list", response);
        return "data/ListView";
    }

    @GetMapping("application/analytics/count-inputs")
    public String countInputs(Model model) {
        List<String> response = audit.countEncodingInputs().entrySet().stream()
                .map(e -> e.getKey() + "  " + e.getValue())
                .collect(Collectors.toList());
        history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));
        model.addAttribute("title", "Inputs count");
        model.addAttribute("list", response);
        return "data/ListView";
    }

    @GetMapping("application/analytics/find-most-popular-codec")
    public String findMostPopular(Model model) {
        model.addAttribute("operation", "find-most-popular-codec");
        model.addAttribute("usecases", CodecUsecase.values());
        return "data/analyticsView";
    }

    @PostMapping("application/analytics/find-most-popular-codec")
    @ResponseBody
    public String findMostPopular(@RequestParam String usecase) {
        CodecUsecase codecUsecase = CodecUsecase.valueOf(usecase);

        Algorithm res = audit.findMostPopularCodec(codecUsecase);
        history.addToHistory(new HistoryRecord(Operation.ANALYTICS, null, null));
        return res.name();
    }
}
