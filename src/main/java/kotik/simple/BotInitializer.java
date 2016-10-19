package kotik.simple;

import kotik.simple.listener.ChatListener;
import kotik.simple.listener.InterfaceListener;
import kotik.simple.service.DiscordClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Roman_Kuznetcov on 19.10.2016.
 */
@Component
public class BotInitializer {

    @Autowired
    private DiscordClient discord;

    @Autowired
    private InterfaceListener interfaceListener;

    @Autowired
    private ChatListener chatListener;

    public BotInitializer(DiscordClient discord) {
        discord.dispather.registerListener(interfaceListener);
        discord.dispather.registerListener(chatListener);
    }
}
