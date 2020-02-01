package org.geekhub.crypto.ui.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {

    @GetMapping("/applicaion/history/clear-history")
    public String clearHistory() {
        return "<form action = \"\" method = \"POST\">" +
                "<input type = \"submit\" value = \"Clear History\"/>" +
                "</form>";
    }

}
