package kotik.simple.service.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Romique on 19.10.2016.
 */
public class DeleteCommand extends AbstractCommand {

    private final static String NAME = "!delete";
	private final static String DESC = "Команда для удаления команд";

    public DeleteCommand(){
        super(NAME, DESC);
    }

    public DeleteCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString(), (String) params.get("permissions"));
    }

    @Override
    public void eval(IMessage message){

        String commandName = message.getContent().substring(8);
        getCommandManager().deleteCommand(commandName);
    }
}
