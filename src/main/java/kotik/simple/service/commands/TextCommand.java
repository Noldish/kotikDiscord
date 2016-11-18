package kotik.simple.service.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;

/**
 * Created by Romique on 19.10.2016.
 */
public class TextCommand extends AbstractCommand {

    public TextCommand(String description){

    }

    public TextCommand(String name, String description){
        super(name, description);
    }

    public TextCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString(), (String) params.get("permissions"));
    }

    @Override
    public void eval(IMessage message){
        getCommandManager().getDiscordService().sendMessage(getDescription(), message.getChannel());
    }
}
