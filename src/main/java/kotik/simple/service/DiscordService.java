package kotik.simple.service;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.gson.JsonSyntaxException;
import kotik.simple.BotUtils;
import kotik.simple.client.warcraftlogs.WarcraftlogsClient;
import kotik.simple.client.warcraftlogs.objects.Shot;
import kotik.simple.client.wowprogress.WowprogressClient;
import kotik.simple.client.wowprogress.objects.Guild;
import kotik.simple.listener.ChatListener;
import kotik.simple.listener.InterfaceListener;
import kotik.simple.dao.objects.User;
import kotik.simple.service.commands.CommandFactory;
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
import sx.blah.discord.handle.impl.obj.VoiceChannel;
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

    private String mainchannel = "235804189240852480"; //raidchyatik
    private String TOKEN;

    private boolean login;
    private boolean initialized;

    private IDiscordClient iDiscordClient;
    private MessageBuilder messageBuilder;
    private EventDispatcher eventDispatcher;
    private IVoiceChannel voiceChannel;

    @javax.annotation.Resource
    private Environment env;

    @Autowired
    private InterfaceListener interfaceListener;

    @Autowired
    private ChatListener chatListener;


    @Autowired
    private UserService userService;

    @Autowired
    private WowprogressClient wowprogress;

    @Autowired
    private WarcraftlogsClient warcraftlogs;

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public DiscordService() {
    }

    @PostConstruct
    public void init() throws DiscordException {
        if (!initialized) {
            System.out.println("Initializing DiscordService");
            ClientBuilder clientBuilder = new ClientBuilder();
            this.TOKEN = env.getRequiredProperty("token");
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

    @PreDestroy
    public void shutdown() throws DiscordException, RateLimitException {
        if (initialized) {
            System.out.println("Stopping DiscordService");
            if (login) {
                iDiscordClient.logout();
                initialized = false;
                login = false;
            }
        } else {
            System.out.println("DiscordService is not initialized");
        }
    }

    public void sendMessage(String message, IChannel channel) {
        try {
            messageBuilder.withContent(message);
            messageBuilder.withChannel(channel);
            messageBuilder.send();
        } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
            e.printStackTrace();
        }
    }

    public void leaveAllVoiceChannels() {
        List<IVoiceChannel> channels = iDiscordClient.getConnectedVoiceChannels();
        for (IVoiceChannel channel : channels) {
            channel.leave();
        }
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

    public void registerUsers() {
        //Update user list with new nicknames or add new users to DB from channel mainchannel
        for (IUser iUser : iDiscordClient.getChannelByID(mainchannel).getUsersHere()) {
            if (userService.getUsers().containsKey(iUser.getID())) {
                userService.getUsers().get(iUser.getID()).setName(iUser.getName());
                userService.getUsers().get(iUser.getID()).setDisplayName(iUser.getDisplayName(iDiscordClient.getChannelByID(mainchannel).getGuild()));
            } else {
                User user = new User();
                user.setId(iUser.getID());
                user.setName(iUser.getName());
                user.setDisplayName(iUser.getDisplayName(iDiscordClient.getChannelByID(mainchannel).getGuild()));
                userService.addUser(user);
            }
        }
    }

    public void showRanking(IMessage message) {

        List<Guild> guilds = wowprogress.getGuildRanking();
        StringBuilder sb = new StringBuilder();
        guilds = guilds.stream().limit(10).collect(Collectors.toList());
        sb.append("```Markdown\n");
        for (Guild guild : guilds) {
            sb.append("* " + guild.toString() + "\n");
        }
        sb.append("```");
        System.out.println(sb.toString());
        sendMessage(sb.toString(), message.getChannel());
    }

    ;


    public UserService getUserService() {
        return userService;
    }

    public IDiscordClient getiDiscordClient() {
        return iDiscordClient;
    }

    public String getMainchannel() {
        return mainchannel;
    }

    public void setMainchannel(String mainchannel) {
        this.mainchannel = mainchannel;
    }

    public void showRankingForPlayer(IMessage message) {

        List<String> params = BotUtils.getCommandParams(message.getContent());

        if (params.size() < 2) {
            sendMessage("Ох тыж Валера. Параметры: ник;сервер", message.getChannel());
            return;
        }

        String characterName = params.get(0);
        String serverName = params.get(1);
        String metric = null;
        Long difficulty = 5l;
        if (params.size() > 2) {
            metric = params.get(2);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("```Markdown\n");

        if (params.size() > 3) {
            difficulty = 4l;
        }


        List<Shot> shots = warcraftlogs.getShotsForPlayer(characterName, serverName, metric, "10", difficulty);
        sb.append("\n               Изумрудный Кошмар: \n\n");
        for (Shot shot : shots) {
            sb.append("* " + shot.toString() + "\n");
        }

        sb.append("\n               Испытание Доблести: \n\n");
        shots = warcraftlogs.getShotsForPlayer(characterName, serverName, metric, "12", difficulty);
        for (Shot shot : shots) {
            sb.append("* " + shot.toString() + "\n");
        }


        sb.append("```");
        sendMessage(sb.toString(), message.getChannel());

    }
}
