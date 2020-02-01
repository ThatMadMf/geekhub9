package org.geekhub.crypto.ui.web.controller;

import org.geekhub.crypto.history.HistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryController {

    private HistoryManager historyManager;

    @Autowired
    public HistoryController(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @GetMapping("application/history/clear-history")
    public String clearHistory() {
        return "<form action = \"\" method = \"POST\">" +
                "<input type = \"submit\" value = \"Clear History\"/>" +
                "</form>";
    }

    @PostMapping("application/history/clear-history")
    public String clearHistoryPost() {
        historyManager.clearHistory();
        return "<p>Removing is success</p>" +
        "<a href=\"/application>Go to menu</a>";
    }

    @GetMapping("application/history/remove-last")
    public String removeLast() {
        return "<form action = \"\" method = \"POST\">" +
                "<input type = \"submit\"/>" +
                "</form>";
    }

    @PostMapping("application/history/remove-last")
    public String removeLastPost() {
        historyManager.removeLastRecord();
        return "<p>Removing is success</p>" +
                "<a href=\"/application>Go to menu</a>";
    }

    @GetMapping("application/history/show-history")
    public String showHistory() {
        StringBuilder response = new StringBuilder();
        historyManager.readHistory()
                .forEach(r -> response.append(r.getStringRepresentation() + "<br>"));
        return response.toString();
    }
}
