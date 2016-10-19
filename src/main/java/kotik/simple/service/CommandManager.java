package kotik.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roman_Kuznetcov on 18.10.2016.
 */
@Service
public class CommandManager {

    @Autowired
    private DiscordService discordService;

    public String handle() {
        return "Anus sebe derni, pes";
    }

    public void message(String message, IChannel channel) {
        if (checkWithRegExp(message)){
            try {
                discordService.sendMessage("Sam svoi komandi vipolniay, pes",channel);
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean checkWithRegExp(String userNameString){
        Pattern p = Pattern.compile("^!.*");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }

}
