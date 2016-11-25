package kotik.simple.service.commands;

import kotik.simple.dao.objects.Sound;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;

/**
 * Created by Romique on 19.10.2016.
 */
public class AddSoundCommand extends AbstractCommand {

    private final static String NAME = "!addsound";
	private final static String DESC = "Команда для добавления звуков";
    private final static String separator = ";";

    public AddSoundCommand(){
        super(NAME, DESC);
    }

    public AddSoundCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString(),(String) params.get("permissions"));
    }

    @Override
    public void eval(IMessage message){
        try {
            String[] parsedMessage = message.getContent().substring(getName().length()).trim().split(separator);
            String name = parsedMessage[0].trim();
            String url = parsedMessage[1].trim();
            String result = getCommandManager().getSoundManager().addSound(new Sound(name,url));
            getCommandManager().getDiscordService().sendMessage(result, message.getChannel());
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

}