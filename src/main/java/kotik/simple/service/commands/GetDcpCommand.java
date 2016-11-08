package kotik.simple.service.commands;

import sx.blah.discord.handle.obj.IMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Romique on 03.11.2016.
 */
public class GetDcpCommand  extends AbstractCommand{

    private final static String NAME = "!getdcp";
    private final static String DESC = "Команда для узнавания своего дцп";
    private List<String> permusers = new ArrayList<>();

    public GetDcpCommand(){
        super(NAME, DESC);
    }

    public GetDcpCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString());
    }

    @Override
    public void eval(IMessage message){
        getCommandManager().getDiscordService().sendMessage("У тебя " + getCommandManager().getDiscordService().getUserService().getUserByName(message.getAuthor().getName()).getDcp() + " дцп!", message.getChannel());
    }

}
