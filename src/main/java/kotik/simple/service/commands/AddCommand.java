package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Romique on 19.10.2016.
 */
public class AddCommand implements CommandInterface {

    private String description = "Команда для добавления текстовых команд";
    private String separator = ";";

    @Override
    public void eval(IMessage message, CommandManager commandManager){
        String[] parsedMessage = message.getContent().substring(4).trim().split(separator);
        String mes = parsedMessage[0];
        String resp = parsedMessage[1];

        commandManager.getDiscordService().sendMessage(commandManager.addCommand(mes, resp), message.getChannel());
    }

    @Override
    public String toString(){
        return description;
    }
}
