package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * Created by Romique on 19.10.2016.
 */
public class FindCommand implements CommandInterface,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5659806616589398046L;

	private Random randomGenerator;

    private String description = "Ищет пидорасов в чате";

    @Override
    public void eval(IMessage message, CommandManager commandManager){
        randomGenerator = new Random();
        String utf8name;
        IUser iUser = message.getChannel().getUsersHere().get(randomGenerator.nextInt(message.getChannel().getUsersHere().size()));
        try {
            commandManager.getDiscordService().sendMessage(new String(iUser.getName().getBytes("Cp1251"), "UTF-8") + " - пидорас!", message.getChannel());
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return description;
    }

}
