package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

import java.io.Serializable;

/**
 * Created by Romique on 19.10.2016.
 */
public class DeleteCommand implements CommandInterface, Serializable {


	private String description = "Команда для удаления команд";

    @Override
    public void eval(IMessage message, CommandManager commandManager){
        String commandName = message.getContent().substring(8);
        commandManager.deleteCommand(commandName);
    }

    @Override
    public String toString(){
        return description;
    }
}
