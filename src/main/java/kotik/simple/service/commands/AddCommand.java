package kotik.simple.service.commands;

import kotik.simple.objects.AbstractCommand;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;

/**
 * Created by Romique on 19.10.2016.
 */
public class AddCommand extends AbstractCommand {
    
    private final static String NAME = "!add";
	private final static String DESC = "Команда для добавления текстовых команд";
    private final static String separator = ";";

    public AddCommand(){
        super(NAME, DESC);
    }

    public AddCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString());
    }

    @Override
    public void eval(IMessage message){
        try {
            String[] parsedMessage = message.getContent().substring(getName().length()).trim().split(separator);
            String mes = parsedMessage[0].trim();
            String resp = parsedMessage[1].trim();
            CommandInterface command = getCommandManager().getCommandFactory().createTextCommand(mes,resp);
            getCommandManager().getDiscordService().sendMessage(getCommandManager().addCommand(command), message.getChannel());
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

}