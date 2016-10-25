package kotik.simple.service;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.gson.JsonSyntaxException;
import kotik.simple.listener.ChatListener;
import kotik.simple.listener.InterfaceListener;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import sx.blah.discord.Discord4J;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.internal.DiscordEndpoints;
import sx.blah.discord.api.internal.DiscordUtils;
import sx.blah.discord.api.internal.Requests;
import sx.blah.discord.api.internal.json.responses.MessageResponse;
import sx.blah.discord.handle.audio.IAudioManager;
import sx.blah.discord.handle.audio.IAudioProvider;
import sx.blah.discord.handle.audio.impl.DefaultProvider;
import sx.blah.discord.handle.impl.events.MessageSendEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.providers.FileProvider;
import sx.blah.discord.util.audio.providers.URLProvider;

/**
 * Created by Romique on 19.10.2016.
 */

@Service
public class DiscordService {

    //private final String TOKEN = "MjM3ODU5MTc0NjIzNjA4ODMz.CueNkA.wUDpk2u6Z6-EQ_bAgljByNuVfpc"; //pur-pur-pur
    private final String TOKEN = "MjMxMDUxMzI1NjI5MTM2ODk2.CukZRg.vzaG9R6xPp2vLWJR4w-9RXbIzAw"; //sobaDoba
    //private final String TOKEN = "MjM4NjA2NDM2OTc4OTE3Mzc2.Cu-SCQ.WbXjKI9qulcGRMQUmaXOf_oHj0c"; //testBotForTestIsTestBot

    private boolean login;
    private boolean initialized;

    private IDiscordClient iDiscordClient;
    private MessageBuilder messageBuilder;
    private EventDispatcher eventDispatcher;
    private IVoiceChannel voiceChannel;

    @Autowired
    private InterfaceListener interfaceListener;

    @Autowired
    private ChatListener chatListener;

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public DiscordService() {

    }

    @PostConstruct
    public void init() throws DiscordException{
        if (!initialized) {
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
            eventDispatcher.registerListener(interfaceListener);
            eventDispatcher.registerListener(chatListener);
            initialized = true;
        } else {
            System.out.println("DiscordService already initialized");
    }
    }

    public void shutdown() throws DiscordException, RateLimitException{
        if (initialized) {
        System.out.println("Stopping DiscordService");
        if (login) {
            iDiscordClient.logout();
        }
        } else {
            System.out.println("DiscordService is not initialized");
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
            voiceChannel = channels.get(0);
			if (!voiceChannel.getName().equals("Адмирал")){
                voiceChannel.join();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        }
    }
    
    public void playSoundToChannelFromFile(IMessage message, String path, String name) {
    	
    	Resource res = new ClassPathResource(path+name);
        IGuild guild = message.getGuild();
        List<IVoiceChannel> channels = message.getAuthor().getConnectedVoiceChannels();
        IAudioManager manager = guild.getAudioManager();
        try {
            IAudioProvider provider = new FileProvider(res.getFile());
            manager.setAudioProvider(provider);
            voiceChannel = channels.get(0);
			if (!voiceChannel.getName().equals("Адмирал")){
                voiceChannel.join();
            }
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
        voiceChannel.leave();
        /*List<IVoiceChannel> channels = guild.getVoiceChannels();
        for (IVoiceChannel channel:channels){
            channel.leave();
        }
        */
    }

    public IMessage sendBytesAsFile(IChannel channel, byte[] bytes, String mime, String filename) throws JsonSyntaxException, RateLimitException, DiscordException, MissingPermissionsException {
        DiscordUtils.checkPermissions(iDiscordClient, channel, EnumSet.of(Permissions.SEND_MESSAGES, Permissions.ATTACH_FILES));

        if (iDiscordClient.isReady()) {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().addBinaryBody("file", bytes, ContentType.create(mime), filename);
            HttpEntity fileEntity = builder.build();
            MessageResponse response = DiscordUtils.GSON.fromJson(Requests.GENERAL_REQUESTS.POST.makeRequest(
                    DiscordEndpoints.CHANNELS + channel.getID() + "/messages",
                    fileEntity, new BasicNameValuePair("authorization", iDiscordClient.getToken())), MessageResponse.class);
            IMessage message = DiscordUtils.getMessageFromJSON(iDiscordClient, channel, response);
            iDiscordClient.getDispatcher().dispatch(new MessageSendEvent(message));
            return message;
        } else {
            Discord4J.LOGGER.error("Bot has not signed in yet!");
            return null;
        }
    }

}
