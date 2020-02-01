package org.geekhub.crypto.ui.web.controller;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.DecoderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class HomeController {

    private DecoderFactory decoderFactory;

    @Autowired
    public HomeController(DecoderFactory decoderFactory) {
        this.decoderFactory = decoderFactory;
    }

    @GetMapping("/")
    public String home() {
        return "<form action=\"\" method=\"POST\">" +
                "Enter User role: <input type=\"text\" name=\"user\">" +
                "<input type=\"submit\" value=\"Submit\"/>" +
                "</form>";
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
    public String application() {
        return "<h2>Crypto application</h2>" +
                "<a href='/application/decode'>1. Decode</a><br>" +
                "<a href='/application/encode'>2. Encode</a><br>" +
                "<a href='/application/analytics'>3. Analytics</a><br>" +
                "<a href='/application/history'>4. History</a><br>";
    }

    @GetMapping("application/history")
    public String history() {
        return "<h2>History operations<h2>" +
                "<a href=\"/geekhub/application/history/show-history\">1. Show history</a><br>" +
                "<a href=\"/geekhub/application/history/remove-last\">2. Remove last element</a><br>" +
                "<a href=\"/geekhub/application/history/clear-history\">3. Clear history</a><br>";
    }

    @GetMapping("application/analytics")
    public String analytics() {
        return "<h2>Analytics</h2>" +
                "<a href=\"/geekhub/application/analytics/count-inputs\">1. Count inputs</a><br>" +
                "<a href=\"/geekhub/application/analytics/count-by-date\">2. Count by date</a><br>" +
                "<a href=\"/geekhub/application/analytics/find-most-popular-codec\">" +
                "3. Find most popular algorithm</a><br>";
    }

    @GetMapping("application/decode")
    public String decode() {
        String result = "";
        result += "<form action = \"\" method = \"POST\">" +
                "Enter decode algorithm: <select name = \"algorithm\">\n";
        for (Algorithm algorithm : Algorithm.values()) {
            result += "<option value=\"" + algorithm.name() + "\">" + algorithm.name() + "</option>";
        }
        result += "</select>" + "<br>" +
                "Enter text to decode: <input name = \"text\">" +
                "<input type = \"submit\" value = \"Submit\"/>" +
                "</form>";
        return result;
    }

    @PostMapping("application/decode")
    public String decodePost(@RequestParam String algorithm, String text) {
        if (algorithm == null) {
            return "Illegal usage of not existing codec";
        }

        return decoderFactory.getDecoder(Algorithm.valueOf(algorithm)).decode(text);
    }

    @GetMapping("application/encode")
    public String encode() {
        String result = "";
        result += "<form action = \"\" method = \"POST\">" +
                "Enter encode algorithm: <select name = \"algorithm\">\n";
        for (Algorithm algorithm : Algorithm.values()) {
            result += "<option value=\"" + algorithm.name() + "\">" + algorithm.name() + "</option>";
        }
        result += "</select>" + "<br>" +
                "Enter text to encode: <input name = \"text\">" +
                "<input type = \"submit\" value = \"Submit\"/>" +
                "</form>";
        return result;
    }

}
