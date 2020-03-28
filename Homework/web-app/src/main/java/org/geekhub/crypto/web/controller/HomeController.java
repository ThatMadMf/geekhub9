package org.geekhub.crypto.web.controller;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.DecoderFactory;
import org.geekhub.crypto.coders.EncoderFactory;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    private DecoderFactory decoderFactory;
    private EncoderFactory encoderFactory;
    private HistoryManager historyManager;

    public HomeController(DecoderFactory decoderFactory, EncoderFactory encoderFactory, HistoryManager historyManager) {
        this.decoderFactory = decoderFactory;
        this.encoderFactory = encoderFactory;
        this.historyManager = historyManager;
    }

    @GetMapping("/")
    public RedirectView root() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/application");
        return redirectView;
    }


    @GetMapping("application")
    public String index() {

        return "menus/application";
    }

    @GetMapping("application/history")
    public String history() {
        return "menus/history";
    }

    @GetMapping("application/analytics")
    public String analytics() {
        return "menus/analytics";
    }

    @GetMapping("application/decode")
    public String decode(Model model) {
        model.addAttribute("link", "decode");
        model.addAttribute("algorithms", Algorithm.values());

        return "codecs";
    }

    @PostMapping("application/decode")
    @ResponseBody
    public String decodePost(@RequestParam Algorithm algorithm, String text) {

        HistoryRecord record = new HistoryRecord(Operation.DECODE, text, algorithm);
        historyManager.addToHistory(record);
        return decoderFactory.getDecoder(algorithm).decode(text);
    }

    @GetMapping("application/encode")
    public String encode(Model model) {
        model.addAttribute("operation", Operation.ENCODE);
        model.addAttribute("algorithms", Algorithm.values());

        return "codecs";
    }

    @PostMapping("application/encode")
    @ResponseBody
    public String encodePost(@RequestParam Algorithm algorithm, String text) {

        HistoryRecord record = new HistoryRecord(Operation.ENCODE, text, algorithm);
        historyManager.addToHistory(record);
        var res =  encoderFactory.getEncoder(algorithm).encode(text);
        return res;
    }
}
