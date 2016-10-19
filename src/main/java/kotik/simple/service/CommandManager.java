package kotik.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
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

    private final String COMMAND_PATTERN = "^!.*";

    @Autowired
    private DiscordService discordService;

    public String handle() {
        return "Anus sebe derni, pes";
    }

    public void process(IMessage message){

        if (isCommand(message.getContent())){
            try {
                discordService.sendMessage("Sam svoi komandi vipolniay, pes",message.getChannel());
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isCommand(String message){
        return message.matches(COMMAND_PATTERN);
    }

}
