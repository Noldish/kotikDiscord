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

    private String EllId = "219739436789923850";

    private Double specialLuck = 0.4;

    private String description = "Ищет пидорасов в чате";

    @Override
    public void eval(IMessage message, CommandManager commandManager) {
        randomGenerator = new Random();
        IUser iUser = message.getChannel().getUsersHere().get(randomGenerator.nextInt(message.getChannel().getUsersHere().size()));
        Double luck = randomGenerator.nextDouble();
        System.out.println(message.getAuthor().getID() + " с ником " + message.getAuthor().getName() + " запросил найти пидораса. " + "Колесо фортуны показало удачу " + luck.toString());
        if (message.getAuthor().getID().equals(EllId)) {
            luck = luck - specialLuck;
            System.out.println("ОГО! Да это же вермион. Насыпем ему удачи ещё: " + luck.toString());
        }
        if (luck > 0.2) {
            System.out.println("Найден пидорас: " + iUser.getDisplayName(message.getGuild()) + " - пидорас!");
            commandManager.getDiscordService().sendMessage(iUser.getDisplayName(message.getGuild()) + " - пидорас!", message.getChannel());
        } else {
            System.out.println("Пацан сам тот ещё петух оказался: " + message.getAuthor().getDisplayName(message.getGuild()) + " - пидорас!");
            commandManager.getDiscordService().sendMessage("В зеркало посмотри, петушок!", message.getChannel());
        }
    }

    @Override
    public String toString(){
        return description;
    }

}
