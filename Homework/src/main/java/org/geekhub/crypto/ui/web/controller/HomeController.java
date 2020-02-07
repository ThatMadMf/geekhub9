package org.geekhub.crypto.ui.web.controller;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.DecoderFactory;
import org.geekhub.crypto.coders.EncoderFactory;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class HomeController {

    private DecoderFactory decoderFactory;
    private EncoderFactory encoderFactory;
    private HistoryManager historyManager;

    @Autowired
    public HomeController(DecoderFactory decoderFactory, EncoderFactory encoderFactory, HistoryManager historyManager) {
        this.decoderFactory = decoderFactory;
        this.encoderFactory = encoderFactory;
        this.historyManager = historyManager;
    }

    @GetMapping("/")
    public String home() {
        return "menus/login";
    }

    @PostMapping("/")
    public void homePost(@RequestParam String user, HttpSession session, HttpServletResponse response)
            throws IOException {
        if (user.equals("admin") || user.equals("user")) {
            session.setAttribute("user", user);
            response.sendRedirect("/application");
        } else {
            response.sendRedirect("/");
        }
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
        model.addAttribute("operation", Operation.DECODE);
        model.addAttribute("algorithms", Algorithm.values());

        return "codecs";
    }

    @PostMapping("application/decode")
    @ResponseBody
    public String decodePost(@RequestParam String algorithm, String text) {
        if (algorithm == null) {
            return "Illegal usage of not existing codec";
        }

        HistoryRecord record = new HistoryRecord(Operation.DECODE, text, Algorithm.valueOf(algorithm));
        historyManager.addToHistory(record);
        return decoderFactory.getDecoder(Algorithm.valueOf(algorithm)).decode(text);
    }

    @GetMapping("application/encode")
    public String encode(Model model) {
        model.addAttribute("operation", Operation.ENCODE);
        model.addAttribute("algorithms", Algorithm.values());

        return "codecs";
    }

    @PostMapping("application/encode")
    @ResponseBody
    public String encodePost(@RequestParam String algorithm, String text) {
        if (algorithm == null) {
            return "Illegal usage of not existing codec";
        }

        HistoryRecord record = new HistoryRecord(Operation.ENCODE, text, Algorithm.valueOf(algorithm));
        historyManager.addToHistory(record);
        return encoderFactory.getEncoder(Algorithm.valueOf(algorithm)).encode(text);
    }
}
