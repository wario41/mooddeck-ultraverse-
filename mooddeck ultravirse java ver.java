import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
@RestController
public class MoodDeckUltraVerse {

    private final List<MoodCard> cards = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(MoodDeckUltraVerse.class, args);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>MoodDeck UltraVerse</title>");
        html.append("<style>body{font-family:'Segoe UI',sans-serif;background:#f0f0f0;padding:20px;text-align:center}");
        html.append(".card{background:white;border-radius:10px;padding:20px;margin:20px auto;width:300px;box-shadow:0 2px 10px rgba(0,0,0,0.1)}");
        html.append(".card h2{margin:0}.card p{font-style:italic}</style></head><body>");
        html.append("<h1>MoodDeck UltraVerse 🎴</h1>");

        html.append("<form method='post' action='/create'>");
        html.append("<select name='emoji'>");
        for (String emoji : Arrays.asList("😄", "😢", "🤯", "🧪", "😎", "😴", "😡", "🤩", "😬")) {
            html.append("<option value='").append(emoji).append("'>").append(emoji).append("</option>");
        }
        html.append("</select><br>");
        html.append("<input type='text' name='title' placeholder='Card Title'><br>");
        html.append("<textarea name='desc' placeholder='Describe your mood...'></textarea><br>");
        html.append("<input type='text' name='quote' placeholder='Optional quote'><br>");
        html.append("<input type='color' name='bgColor' value='#ffffff'><br>");
        html.append("<input type='hidden' name='userId' value='demo-user'>");
        html.append("<button type='submit'>Create Mood Card</button></form>");

        html.append("<form method='post' action='/chaos'>");
        html.append("<input type='hidden' name='userId' value='demo-user'>");
        html.append("<button type='submit'>🧪 Wario41 Chaos</button></form>");

        for (MoodCard card : cards) {
            html.append("<div class='card' style='background-color:").append(card.bgColor).append("'>");
            html.append("<h2>").append(card.emoji).append(" ").append(card.title).append("</h2>");
            html.append("<p>").append(card.desc).append("</p>");
            if (card.quote != null && !card.quote.isEmpty()) {
                html.append("<blockquote>\"").append(card.quote).append("\"</blockquote>");
            }
            html.append("</div>");
        }

        html.append("</body></html>");
        return html.toString();
    }

    @PostMapping("/create")
    public String createCard(@RequestParam String emoji,
                             @RequestParam String title,
                             @RequestParam String desc,
                             @RequestParam String quote,
                             @RequestParam String bgColor,
                             @RequestParam String userId) {
        MoodCard card = new MoodCard(emoji, title, desc, quote, bgColor, LocalDateTime.now().toString(), userId);
        cards.add(card);
        return "<meta http-equiv='refresh' content='0; url=/' />";
    }

    @PostMapping("/chaos")
    public String chaosCard(@RequestParam String userId) {
        MoodCard chaos = new MoodCard(
                pick(new String[]{"😄", "😢", "🤯", "🧪", "😎", "😴", "😡", "🤩", "😬"}),
                pick(new String[]{"Chaos Mode", "Wario41 Surge", "UltraVerse Glitch", "Moodquake", "Emoji Storm"}),
                pick(new String[]{"Unpredictable vibes", "Reality bending", "Mood overload", "Feeling everything", "No filter"}),
                pick(new String[]{"Let the chaos begin", "Wario41 was here", "MoodDeck unleashed"}),
                "#" + Integer.toHexString(new Random().nextInt(0xFFFFFF)),
                LocalDateTime.now().toString(),
                userId
        );
        cards.add(chaos);
        return "<meta http-equiv='refresh' content='0; url=/' />";
    }

    private String pick(String[] options) {
        return options[new Random().nextInt(options.length)];
    }

    static class MoodCard {
        String emoji, title, desc, quote, bgColor, timestamp, userId;
        MoodCard(String emoji, String title, String desc, String quote, String bgColor, String timestamp, String userId) {
            this.emoji = emoji;
            this.title = title;
            this.desc = desc;
            this.quote = quote;
            this.bgColor = bgColor;
            this.timestamp = timestamp;
            this.userId = userId;
        }
    }
  }
