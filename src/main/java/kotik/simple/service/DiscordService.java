package kotik.simple.service;

import java.io.IOException;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.stereotype.Service;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.audio.IAudioManager;
import sx.blah.discord.handle.audio.IAudioProvider;
import sx.blah.discord.handle.audio.impl.DefaultProvider;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.providers.URLProvider;

/**
 * Created by Romique on 19.10.2016.
 */

@Service
public class DiscordService {

    //private final String TOKEN = "MjM3ODU5MTc0NjIzNjA4ODMz.CueNkA.wUDpk2u6Z6-EQ_bAgljByNuVfpc"; //pur-pur-pur
    //private final String TOKEN = "MjMxMDUxMzI1NjI5MTM2ODk2.CukZRg.vzaG9R6xPp2vLWJR4w-9RXbIzAw"; //sobaDoba
    private final String TOKEN = "MjM4NjA2NDM2OTc4OTE3Mzc2.Cu-SCQ.WbXjKI9qulcGRMQUmaXOf_oHj0c"; //testBotForTestIsTestBot

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

    public void playSoundToChannelFromURL(IMessage message, String url) {
        IGuild guild = message.getGuild();
        List<IVoiceChannel> channels = message.getAuthor().getConnectedVoiceChannels();
        IAudioManager manager = guild.getAudioManager();
        try {
            IAudioProvider provider = new URLProvider(url);
            manager.setAudioProvider(provider);
            manager.getAudioProcessor().provide();
            channels.get(0).join();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        }
    }

    public void stopSound(IMessage message) {
        IGuild guild = message.getGuild();
        IAudioManager manager = guild.getAudioManager();
        manager.setAudioProvider(new DefaultProvider());
        List<IVoiceChannel> channels = guild.getVoiceChannels();
        for (IVoiceChannel channel:channels){
            channel.leave();
        }
    }

}
