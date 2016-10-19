package kotik.simple.service;

import org.springframework.stereotype.Component;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@Component
public class DiscordClient {

    private IDiscordClient discordClient;
    private MessageBuilder messageBuilder;

    public EventDispatcher dispather;

    public DiscordClient(){
        try {
            this.discordClient = getClient("MjM3ODU5MTc0NjIzNjA4ODMz.CueNkA.wUDpk2u6Z6-EQ_bAgljByNuVfpc" ,true);
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        this.dispather = this.discordClient.getDispatcher();
        this.messageBuilder = new MessageBuilder(this.discordClient);
    };

    private static IDiscordClient getClient(String token, boolean login) throws DiscordException { // Returns an instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        if (login) {
            return clientBuilder.login(); // Creates the client instance and logs the client in
        } else {
            return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
        }
    }

    public void sendMessage(String message, IChannel channel) throws RateLimitException, DiscordException, MissingPermissionsException {
        messageBuilder.withContent(message);
        messageBuilder.withChannel(channel);
        messageBuilder.send();
    }
}
