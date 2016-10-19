package kotik.simple.service;

import org.springframework.stereotype.Service;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Romique on 19.10.2016.
 */

@Service
public class DiscordService {

    private final String TOKEN = "MjM3ODU5MTc0NjIzNjA4ODMz.CueNkA.wUDpk2u6Z6-EQ_bAgljByNuVfpc";

    private boolean login;

    private IDiscordClient iDiscordClient;
    private MessageBuilder messageBuilder;
    private EventDispatcher eventDispatcher;

    public IDiscordClient getiDiscordClient() {
        return iDiscordClient;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public boolean isLogin() {
        return login;
    }

    public void init() throws DiscordException{
        System.out.println("Initializing DiscordService");
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(TOKEN);
        if (login) {
            iDiscordClient = clientBuilder.login();
        } else {
            iDiscordClient = clientBuilder.build();
            iDiscordClient.login();
            login = true;
        }
        messageBuilder = new MessageBuilder(iDiscordClient);
        eventDispatcher = iDiscordClient.getDispatcher();
    }

    public void shutdown() throws DiscordException, RateLimitException{
        System.out.println("Stopping DiscordService");
        if (login) {
            iDiscordClient.logout();
        }
    }

    public void sendMessage(String message, IChannel channel){
        try {
            messageBuilder.withContent(message);
            messageBuilder.withChannel(channel);
            messageBuilder.send();
        } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
            e.printStackTrace();
        }
    }
}
