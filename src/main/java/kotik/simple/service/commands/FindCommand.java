package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.Serializable;
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
        System.out.println(message.getAuthor().getName() + " запросил найти пидораса.");
        randomGenerator = new Random();
        IUser iUser = message.getChannel().getUsersHere().get(randomGenerator.nextInt(message.getChannel().getUsersHere().size()));
        //try {
            Double luck = randomGenerator.nextDouble();
            if (message.getAuthor().getName().equals("Ellq")){
                luck = luck - 0.5;
            }
            System.out.println("Поиск пидораса");
            if (luck > 0.2) {
                //System.out.println("Найден пидорас: " + new String(iUser.getDisplayName(message.getGuild()).getBytes("Cp1251"), "UTF-8") + " - пидорас!");
                //commandManager.getDiscordService().sendMessage(new String(iUser.getDisplayName(message.getGuild()).getBytes("Cp1251"), "UTF-8") + " - пидорас!", message.getChannel());
                System.out.println("Найден пидорас: " + iUser.getDisplayName(message.getGuild()) + " - пидорас!");
                commandManager.getDiscordService().sendMessage(iUser.getDisplayName(message.getGuild()) + " - пидорас!", message.getChannel());
            } else {
                System.out.println("Пацан сам тот ещё петух оказался: " + iUser.getDisplayName(message.getGuild()) + " - пидорас!");
                commandManager.getDiscordService().sendMessage("В зеркало посмотри, петушок!", message.getChannel());
            }
      //  } catch (UnsupportedEncodingException e){
        //    e.printStackTrace();
        //}
    }

    @Override
    public String toString(){
        return description;
    }

}
