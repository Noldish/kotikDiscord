package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Romique on 03.11.2016.
 */
public class AddDcpCommand extends AbstractCommand{

    private final static String NAME = "!adddcp";
    private final static String DESC = "Команда для добавления дцп";
    private final static String separator = ";";

    public AddDcpCommand(){
        super(NAME, DESC);
    }

    public AddDcpCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString());
    }

    @Override
    public void eval(IMessage message){
        if (getPermitted_userlist().contains(message.getAuthor().getID())) {
            String[] parsedMessage = message.getContent().substring(8).trim().split(separator);
            String user = parsedMessage[0].trim();
            String dcp = parsedMessage[1].trim();
            getCommandManager().getDiscordService().getUserService().addDcp(user, dcp);
            getCommandManager().getDiscordService().sendMessage("Ok", message.getChannel());
        } else {
            getCommandManager().getDiscordService().sendMessage("Пес, ты кто такой, чтобы дцп раздавать?", message.getChannel());
        }
    }

}
