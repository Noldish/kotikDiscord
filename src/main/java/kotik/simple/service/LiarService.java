package kotik.simple.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Romique on 25.10.2016.
 */
@Service
public class LiarService {
    @Autowired
    DiscordService discordService;
    private Map<String, Double> weights;
    private Map<String, Double> trustedGuys;
    private static Double threshold = 0.5;
    private static final String memeURL = "http://risovach.ru/generator/preview?id=687075";

    public void addWord(String word){
        weights.put(word, new Double(countWords(word)));
    }

    public int countWords(String s) {
        int wordCount = 0;
        boolean word = false;
        int endOfLine = s.length() - 1;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetterOrDigit(s.charAt(i)) && i != endOfLine) {
                word = true;
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }


    public boolean isTriggered(IMessage message) {
        Double weight = 0.0;
        Double score = 0.0;
        Integer wordsCount = countWords(message.getContent());
        for (Map.Entry<String,Double> e : trustedGuys.entrySet()){
            if (message.getAuthor().getName().equals(e.getKey())){
                score = score + e.getValue();
            }
        }
        for (Map.Entry<String, Double> e : weights.entrySet()) {
            if (message.getContent().toLowerCase().contains(e.getKey().toLowerCase())) {
                score = score + e.getValue();
            }
        }
        weight = 1.0 * (score / wordsCount);
        return weight >= threshold;
    }

    @PostConstruct
    public void init() {
        trustedGuys = new HashMap<>();
        weights = new HashMap<>();
        trustedGuys.put("Ценитель", 0.2);
        trustedGuys.put("Ellq", 0.4);
        addWord("я");
        addWord("крутой");
        addWord("лучший");
        addWord("топ");
        addWord("не фейлил");
        addWord("не фейлю");
        addWord("в говно не попадал");
        addWord("в говно не попадаю");
        addWord("я нормально");
        addWord("нормально я");
        addWord("я хорошо");
        addWord("хорошо я");
        addWord("перехилю");
        addWord("передамажу");
        addWord("я круче");
        addWord("я популярный");
        addWord("я лучше всех");
        addWord("доверься мне");
        addWord("не жру говно");
        addWord("я не жрал говно");
        addWord("я не жрал говна");
        addWord("не косячу");
        addWord("я не косячил");
    }

    public void process(IMessage message){
        if (isTriggered(message)){
            try {
                message.delete();
                String url = memeURL + "&text2=" + URLEncoder.encode(message.getAuthor().getName() + " : " + message.getContent(), "UTF-8");
                InputStream is = new URL(url).openStream();
                byte[] img = IOUtils.toByteArray(is);
                discordService.sendBytesAsFile(message.getChannel(), img, "image/jpeg", "message.jpg");
                //discordService.sendMessage(url, message.getChannel());
            } catch (MissingPermissionsException | RateLimitException | DiscordException e){
                e.printStackTrace();
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
