package kotik.simple.service;

import kotik.simple.BotUtils;
import kotik.simple.dao.RepositoryManager;
import kotik.simple.service.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.obj.IMessage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman_Kuznetcov on 18.10.2016.
 */
@Service
public class CommandManager {

	private final String COMMAND_PATTERN = "^!.+";
    private final String WRONG_COMMAND_MESSAGE = "Сам свои команды выполняй, пёс!";
    private final static String TABLE = "commands";
	private Map<String, CommandInterface> commands = new HashMap<String, CommandInterface>();

	@Autowired
	private DiscordService discordService;

    @Autowired LiarService liarService;

    @Autowired
    CommandFactory commandFactory;

    @Autowired
    RepositoryManager repository;

	public CommandManager() {
	}

    @PostConstruct
    public void init(){
        commands = repository.getAllCommands();
    }

	public String handle() {
		return "Anus sebe derni, pes";
	}

	public void process(IMessage message) {
        if (isCommand(message.getContent())) {
            processCommmand(message);
        }
    }

	public boolean isCommand(String message) {
		return message.matches(COMMAND_PATTERN);
	}

	public void processCommmand(IMessage message) {
        Boolean flag = false;
        String commandKey = BotUtils.getCommandKey(message.getContent());
        for (Map.Entry<String, CommandInterface> e : commands.entrySet()){
            if ((!flag) && (matchCommand(commandKey, e.getKey()))){
                e.getValue().eval(message);
                flag = true;
            }
        }
        if (!flag){
            discordService.sendMessage(WRONG_COMMAND_MESSAGE, message.getChannel());
        }
	}
	
	private Boolean matchCommand(String message, String command){
        if (!message.matches("^" + command + ".*")){    //Если начинается сообщение не как команда
            return false;
        } else if (command.length() == message.length()) { //Если целое сообщение = целой команде
            return true;
        } else if (!Character.isWhitespace(message.charAt(command.length()))){  //Если оно начинается как команда, но после этого идет не пробел. Например !addhui
            return false;
        } else {
            return true;  //Если сообщение не совпадает с командой, при этом начинается с команды, после чего идет пробел
        }
	}

	public String addCommand(String message, CommandInterface command) {
        if (!commands.containsKey(message)) {
            repository.addCommand(command);
            commands.put(message, command);
            return "Ok";
        } else {
            return "This command already exists";
        }
    }

    public String addCommand(CommandInterface command) {
        String message = command.getName();
        return addCommand(message, command);
    }

    //TODO ПЕРЕДЕЛАТЬ ЭТО
	public String deleteCommand(String message) {
		if (commands.containsKey(message)) {
            commands.remove(message);
            repository.deleteCommand(message);
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

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }
}
