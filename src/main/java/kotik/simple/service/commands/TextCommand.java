package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Romique on 19.10.2016.
 */
public class TextCommand implements CommandInterface {
    private String text;

    public TextCommand(String text){
        this.text = text;
    }

    @Override
    public void eval(IMessage message, CommandManager commandManager){
        commandManager.getDiscordService().sendMessage(text, message.getChannel());
    }

    @Override
    public String toString(){
        return text;
    }
}
