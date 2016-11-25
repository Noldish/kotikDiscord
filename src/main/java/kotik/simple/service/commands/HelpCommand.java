package kotik.simple.service.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;

/**
 * Created by Romique on 19.10.2016.
 */
public class HelpCommand extends AbstractCommand {

    private final static String NAME = "!help";
	private final static String DESC = "Показывает список команд";

    public HelpCommand() {
        super(NAME, DESC);
    }

    public HelpCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString(), (String) params.get("permissions"));
    }


    @Override
    public void eval(IMessage message){
        StringBuilder sb = new StringBuilder();
        sb.append("**Доступные команды:**");
        sb.append("```xml\n");
        for (String command : getCommandManager().getCommands().keySet()){
            sb.append("<"+fixedLengthString(command+">",25));
        }
        sb.append("```");
        getCommandManager().getDiscordService().sendMessage(sb.toString(), message.getChannel());
    }


    public static String fixedLengthString(String string, int length) {
        return String.format("%1$"+(-length)+ "s", string);
    }
}
