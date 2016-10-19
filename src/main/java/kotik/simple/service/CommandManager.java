package kotik.simple.service;

import kotik.simple.service.commands.*;
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
    private final String ADD_COMMAND_PATTERN = "^!add.*";
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
        addCommand("!add", new AddCommand());
    }

    public String handle() {
        return "Anus sebe derni, pes";
    }

    public void process(IMessage message){
        if (isAddCommand(message.getContent())){
            commands.get("!add").eval(message, this);
        } else if (isCommand(message.getContent())){
            processCommmand(message);
        }
    }

    public boolean isCommand(String message){
        return message.matches(COMMAND_PATTERN);
    }

    public boolean isAddCommand(String message){
        return message.matches(ADD_COMMAND_PATTERN);
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
