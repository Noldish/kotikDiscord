package kotik.simple.service.commands;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Romique on 19.10.2016.
 */
public class FindCommand extends AbstractCommand {

    private final static String NAME = "!найди пидораса";
    private final static String DESC = "Ищет пидорасов в чате";

    public FindCommand(){
        super(NAME, DESC);
    }

    public FindCommand(HashMap params){
        super(params.get("name").toString(), params.get("description").toString());
    }

    @Override
    public void eval(IMessage message) {
        Random randomGenerator;
        String EllId = "219739436789923850";
        Double specialLuck = 0.4;
        randomGenerator = new Random();
        IUser iUser = message.getChannel().getUsersHere().get(randomGenerator.nextInt(message.getChannel().getUsersHere().size()));
        Double luck = randomGenerator.nextDouble();
        System.out.println(message.getAuthor().getID() + " с ником " + message.getAuthor().getName() + " запросил найти пидораса. " + "Колесо фортуны показало удачу " + luck.toString());
        if (message.getAuthor().getID().equals(EllId)) {
            luck = luck - specialLuck;
            System.out.println("ОГО! Да это же вермион. Насыпем ему удачи ещё: " + luck.toString());
        }
        if ((luck > 0.2) && (message.getAuthor().getName() != iUser.getName())) {
            System.out.println("Найден пидорас: " + iUser.getDisplayName(message.getGuild()) + " - пидорас!");
            getCommandManager().getDiscordService().sendMessage(iUser.getDisplayName(message.getGuild()) + " - пидорас!", message.getChannel());
        } else {
            System.out.println("Пацан сам тот ещё петух оказался: " + message.getAuthor().getDisplayName(message.getGuild()) + " - пидорас!");
            getCommandManager().getDiscordService().sendMessage("В зеркало посмотри, петушок!", message.getChannel());
        }
    }

}
