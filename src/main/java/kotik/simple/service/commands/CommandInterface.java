package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

import java.io.Serializable;

/**
 * Created by Romique on 19.10.2016.
 */
public interface CommandInterface {

    public void eval(IMessage message, CommandManager commandManager);

    public String toString();
}
