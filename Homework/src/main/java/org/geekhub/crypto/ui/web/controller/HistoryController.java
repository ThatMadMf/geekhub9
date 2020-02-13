package org.geekhub.crypto.ui.web.controller;

import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HistoryController {

    private HistoryManager historyManager;

    public HistoryController(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @GetMapping("application/history/clear-history")
    public String clearHistory(Model model) {
        model.addAttribute("operation", "clear-history");
        return "data/historyView";
    }

    @PostMapping("application/history/clear-history")
    public String clearHistoryPost() {
        historyManager.clearHistory();
        return "data/historyDeleteComplete";
    }

    @GetMapping("application/history/remove-last")
    public String removeLast(Model model) {
        model.addAttribute("operation", "remove-last");
        return "data/historyView";
    }

    @PostMapping("application/history/remove-last")
    public String removeLastPost() {
        historyManager.removeLastRecord();
        return "data/historyDeleteComplete";
    }

    @GetMapping("application/history/show-history")
    public String showHistory(Model model) {
        List<String> response = historyManager.readHistory().stream()
                .map(HistoryRecord::getStringRepresentation)
                .collect(Collectors.toList());
        model.addAttribute("title", "History Records");
        model.addAttribute("list", response);
        return "data/ListView";
    }
}
