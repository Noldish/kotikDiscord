package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

import java.io.Serializable;

/**
 * Created by Romique on 19.10.2016.
 */
public class TextCommand implements CommandInterface,Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7132931201182285689L;
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
