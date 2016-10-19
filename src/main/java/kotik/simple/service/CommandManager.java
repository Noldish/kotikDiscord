package kotik.simple.service;

import kotik.simple.service.commands.CommandInterface;
import kotik.simple.service.commands.FindCommand;
import kotik.simple.service.commands.HelpCommand;
import kotik.simple.service.commands.TextCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.obj.IMessage;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman_Kuznetcov on 18.10.2016.
 */
@Service
public class CommandManager {

    private final String COMMAND_PATTERN = "^!.*";
    private Map<String, CommandInterface> commands = new HashMap<String, CommandInterface>();

    @Autowired
    private DiscordService discordService;


    public CommandManager(){
        addCommand("!гуся", new TextCommand("░ГУСЯ░▄▀▀▀▄░РАБОТЯГИ░░ \n" +
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
                "░░░░░░░░░▄▄▌▌▄▌▌░░░░░"));
        addCommand("!пюрешка", new TextCommand("https://www.youtube.com/watch?v=A1Qb4zfurA8"));
        addCommand("!найди пидораса", new FindCommand());
        addCommand("!help", new HelpCommand());
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
        if (commands.containsKey(message.getContent())){
            commands.get(message.getContent()).eval(message, this);
        } else {
            discordService.sendMessage("Sam svoi komandi vipolniay, pes", message.getChannel());
        }
    }

    public String addCommand(String message, String response){
        if (!commands.containsKey(message)) {
            commands.put(message, new TextCommand(response));
            return "Ok";
        } else {
            return "This command already exists";
        }
    }

    public String addCommand(String message, CommandInterface commandInterface){
        if (!commands.containsKey(message)) {
            commands.put(message, commandInterface);
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

    public Map<String, CommandInterface> getCommands() {
        return commands;
    }

    public DiscordService getDiscordService() {
        return discordService;
    }
}
