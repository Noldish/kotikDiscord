package kotik.simple.service;

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


	public CommandManager() {
	}

    @PostConstruct
    public void init(){
//        for (DBData data : dbUtils.getData(TABLE)) {
//            CommandInterface command = commandFactory.createFromDBData(data);
//            this.commands.put(command.getName(), command);
//        }
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
        for (Map.Entry<String, CommandInterface> e : commands.entrySet()){
            if ((!flag) && (matchCommand(message, e.getKey()))){
                e.getValue().eval(message);
                flag = true;
            }
        }
        if (!flag){
            discordService.sendMessage(WRONG_COMMAND_MESSAGE, message.getChannel());
        }
	}
	
	private Boolean matchCommand(IMessage message, String command){
        if (!message.getContent().matches("^" + command + ".*")){    //Если начинается сообщение не как команда
            return false;
        } else if (command.length() == message.getContent().length()) { //Если целое сообщение = целой команде
            return true;
        } else if (!Character.isWhitespace(message.getContent().charAt(command.length()))){  //Если оно начинается как команда, но после этого идет не пробел. Например !addhui
            return false;
        } else {
            return true;  //Если сообщение не совпадает с командой, при этом начинается с команды, после чего идет пробел
        }
	}

	public String addCommand(String message, CommandInterface command) {
        if (!commands.containsKey(message)) {
//            dbUtils.insertData(generateDBData(command));
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
			return "Ok";
		} else {
			return "This command doesn't exist";
		}
	}


//    public DBData generateDBData(CommandInterface command){
//        DBData dbData = new DBData();
//        dbData.setTable(TABLE);
//        dbData.add("class", command.getClass().getName());
//        dbData.add("name", command.getName());
//        dbData.add("description", command.getDescription());
//        StringBuilder sb = new StringBuilder();
//        if (command.getPermitted_userlist().size() > 0) {
//            for (String permission : command.getPermitted_userlist()) {
//                sb.append(permission).append(",");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            dbData.add("permissions", sb.toString());
//        }
//        return dbData;
//    }

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
