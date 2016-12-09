package kotik.simple.service.commands;

import kotik.simple.BotUtils;
import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Roman_Kuznetcov on 21.11.2016.
 */
public class RankingCommand extends AbstractCommand {

    private final static String NAME = "!ranking";
    private final static String DESC = "Ранкинг на сервере";

    public RankingCommand() {
        super(NAME, DESC);
    }

    public RankingCommand(HashMap params) {
        super(params.get("name").toString(), params.get("description").toString(),(String) params.get("permissions"));
    }


    @Override
    public void eval(IMessage message) {
        List<String> params = BotUtils.getCommandParams(message.getContent());
        if (params.isEmpty()){
            getCommandManager().getDiscordService().showRanking(message);
        } else {
            getCommandManager().getDiscordService().showRankingForPlayer(message);
        }

    }
}
