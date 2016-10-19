package kotik.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman_Kuznetcov on 18.10.2016.
 */
@Service
public class CommandManager {

    private final String COMMAND_PATTERN = "^!.*";
    private Map<String, String> commands = new HashMap<String, String>();

    @Autowired
    private DiscordService discordService;


    public CommandManager(){
        addCommand("!гуся", "░ГУСЯ░▄▀▀▀▄░РАБОТЯГИ░░ \n" +
                "▄███▀░◐░░░▌░░░░░░░ \n" +
                "░░░░▌░░░░░▐░░░░░░░ \n" +
                "░░░░▐░░░░░▐░░░░░░░ \n" +
                "░░░░▌░░░░░▐▄▄░░░░░ \n" +
                "░░░░▌░░░░▄▀▒▒▀▀▀▀▄ \n" +
                "░░░▐░░░░▐▒▒▒▒▒▒▒▒▀▀▄ \n" +
                "░░░▐░░░░▐▄▒▒▒▒▒▒▒▒▒▒▀▄ \n" +
                "░░░░▀▄░░░░▀▄▒▒▒▒▒▒▒▒▒▒▀▄ \n" +
                "░░░░░░▀▄▄▄▄▄█▄▄▄▄▄▄▄▄▄▄▄▀▄ \n" +
                "░░░░░░░░░░░▌▌░▌▌░░░░░ \n" +
                "░░░░░░░░░░░▌▌░▌▌░░░░░ \n" +
                "░░░░░░░░░▄▄▌▌▄▌▌░░░░░");
        addCommand("!найди пидораса", "Вермион пидорас");
        addCommand("!пюрешка", "https://www.youtube.com/watch?v=A1Qb4zfurA8");
    }

    public String handle() {
        return "Anus sebe derni, pes";
    }

    public void process(IMessage message){
        if (isCommand(message.getContent())){
            processCommmand(message);
        }
    }

    public boolean isCommand(String message){
        return message.matches(COMMAND_PATTERN);
    }

    public void processCommmand(IMessage message) {
        try {
            if (commands.containsKey(message.getContent())) {
                discordService.sendMessage(commands.get(message.getContent()), message.getChannel());
            } else {
                discordService.sendMessage("Sam svoi komandi vipolniay, pes",message.getChannel());
            }
        } catch (RateLimitException | DiscordException | MissingPermissionsException e){
            e.printStackTrace();
        }
    }

    public String addCommand(String message, String response){
        System.out.println("Adding command: message=" + message + "; response = " + response);
        if (!commands.containsKey(message)) {
            commands.put(message, response);
            return "Ok";
        } else {
            return "This command already exists";
        }
    }

    public String deleteCommand(String message){
        if (commands.containsKey(message)){
            commands.remove(message);
            return "Ok";
        } else {
            return "This command doesn't exist";
        }
    }

    public Map<String, String> getCommands() {
        return commands;
    }
}
