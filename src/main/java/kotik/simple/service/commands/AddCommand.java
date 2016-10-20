package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

import java.io.Serializable;

/**
 * Created by Romique on 19.10.2016.
 */
public class AddCommand implements CommandInterface, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 929114006085728772L;
	private String description = "Команда для добавления текстовых команд";
    private String separator = ";";

    @Override
    public void eval(IMessage message, CommandManager commandManager){
        String[] parsedMessage = message.getContent().substring(4).trim().split(separator);
        String mes = parsedMessage[0].trim();
        String resp = parsedMessage[1].trim();

        commandManager.getDiscordService().sendMessage(commandManager.addCommand(mes, resp), message.getChannel());
    }

    @Override
    public String toString(){
        return description;
    }
}
