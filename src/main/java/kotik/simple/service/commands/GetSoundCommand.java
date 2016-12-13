package kotik.simple.service.commands;

import kotik.simple.BotUtils;
import kotik.simple.dao.objects.Sound;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;
import java.util.List;

public class GetSoundCommand extends AbstractCommand {

    private final static String NAME = "!playsound";
    private final static String DESC = "Команда для воспроизведения звуков";

    public GetSoundCommand(){
        super(NAME, DESC);
    }

    public GetSoundCommand(String name, String description) {
        super(name, description);
    }

    public GetSoundCommand(HashMap params) {
        super(params.get("name").toString(), params.get("description").toString(), (String) params.get("permissions"));
    }

    @Override
    public void eval(IMessage message) {

        if (isNoSoundSelected(message.getContent())) {
            getCommandManager().getSoundManager().showSoundList(message.getChannel());
        } else{
            getCommandManager().getSoundManager().playSound(BotUtils.getCommandParams(message.getContent()),message);
        }
    }

    private boolean isNoSoundSelected(String messageContext) {
         return BotUtils.getCommandParams(messageContext).isEmpty()?true:false;
    }

}
