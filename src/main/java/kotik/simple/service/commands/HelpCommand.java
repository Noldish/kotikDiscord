package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

import java.io.Serializable;

/**
 * Created by Romique on 19.10.2016.
 */
public class HelpCommand implements CommandInterface,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7733204644919987120L;
	private String description = "Показывает список команд";

    @Override
    public void eval(IMessage message, CommandManager commandManager){
        StringBuilder sb = new StringBuilder();
        sb.append("Доступные команды:  ");
        for (String command : commandManager.getCommands().keySet()){
            sb.append(command).append("  ");
        }
        commandManager.getDiscordService().sendMessage(sb.toString(), message.getChannel());
    }

    @Override
    public String toString(){
        return description;
    }
}
