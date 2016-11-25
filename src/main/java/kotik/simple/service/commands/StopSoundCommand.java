package kotik.simple.service.commands;

import kotik.simple.dao.objects.Sound;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;

/**
 * Created by Romique on 19.10.2016.
 */
public class StopSoundCommand extends AbstractCommand {

    private final static String NAME = "!stopdj";
	private final static String DESC = "Команда для остановки воспроизведения звука";

    public StopSoundCommand(){
        super(NAME, DESC);
    }

    public StopSoundCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString(),(String) params.get("permissions"));
    }

    @Override
    public void eval(IMessage message){
            getCommandManager().getSoundManager().stopSound(message);
    }

}