package kotik.simple;

import kotik.simple.listener.ChatListener;
import kotik.simple.listener.InterfaceListener;
import kotik.simple.service.DiscordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Romique on 19.10.2016.
 */
@RestController
public class MyController {
    @Autowired
    DiscordService discordService;

    @Autowired
    private InterfaceListener interfaceListener;

    @Autowired
    private ChatListener chatListener;

    @RequestMapping("/start")
    public void start() throws DiscordException{
        discordService.init();
        discordService.getEventDispatcher().registerListener(interfaceListener);
        discordService.getEventDispatcher().registerListener(chatListener);
    }

    @RequestMapping("/stop")
    public void stop() throws DiscordException, RateLimitException{
        discordService.shutdown();
    }
}
