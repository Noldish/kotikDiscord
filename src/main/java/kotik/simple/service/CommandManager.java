package kotik.simple.service;

import kotik.simple.service.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
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

	private CommandRepository commandRepository;

	@Autowired
	private DiscordService discordService;

    @Autowired LiarService liarService;

	@Autowired
	public CommandManager(CommandRepository commandRepository) {
		this.commandRepository = commandRepository;
		this.commands = commandRepository.getCommandsList();
	}

	public String handle() {
		return "Anus sebe derni, pes";
	}

	public void process(IMessage message) {
        liarService.process(message);
		if (isAddCommand(message.getContent())) {
			commands.get("!add").eval(message, this);
		} else if (isCommand(message.getContent())) {
			processCommmand(message);
		}
	}

	public boolean isCommand(String message) {
		return message.matches(COMMAND_PATTERN);
	}

	public boolean isAddCommand(String message) {
		return message.matches(ADD_COMMAND_PATTERN);
	}

	public void processCommmand(IMessage message) {
		String commandKey = getCommandKey(message);
		if (commands.containsKey(commandKey)) {
			commands.get(commandKey).eval(message, this);
		} else {
			discordService.sendMessage("Sam svoi komandi vipolniay, pes", message.getChannel());
		}
	}
	
	private String getCommandKey(IMessage message){
		String result = message.getContent();
		if (result.indexOf(' ')!=-1){
			result = result.substring(0, result.indexOf(' '));
		}
		return result;
	}

	public String addCommand(String message, String response) {
		if (!commands.containsKey(message)) {
			if (commandRepository.addCommand(message, new TextCommand(response))) {
				this.commands = commandRepository.getCommandsList();
				return "Ok";
			} else {
				return "Can't add new command. Database exception";
			}
		} else {
			return "This command already exists";
		}
	}

	public String addCommand(String message, CommandInterface commandInterface) {
		if (!commands.containsKey(message)) {
			if (commandRepository.addCommand(message, commandInterface)) {
				this.commands = commandRepository.getCommandsList();
				return "Ok";
			} else {
				return "Can't add new command. Database exception";
			}
		} else {
			return "This command already exists";
		}
	}

	public String deleteCommand(String message) {
		if (commands.containsKey(message)) {
			commandRepository.deleteCommand(message);
			this.commands = commandRepository.getCommandsList();
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
