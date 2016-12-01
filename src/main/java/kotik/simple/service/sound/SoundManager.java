package kotik.simple.service.sound;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kotik.simple.dao.RepositoryManager;
import kotik.simple.dao.objects.Sound;
import kotik.simple.service.DiscordService;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.audio.IAudioProvider;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Roman_Kuznetcov on 25.11.2016.
 */
@Service
public class SoundManager {

    @Autowired
    private RepositoryManager repository;

    @Autowired
    private DiscordService discord;

    private AudioPlayerManager playerManager;

    private TrackScheduler trackScheduler;

    @PostConstruct
    public void init() {
        playerManager = new DefaultAudioPlayerManager();
        playerManager.getConfiguration().setResamplingQuality(AudioConfiguration.ResamplingQuality.LOW);
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        playerManager.setFrameBufferDuration((int) TimeUnit.SECONDS.toMillis(20L));
        playerManager.registerSourceManager(new SoundCloudAudioSourceManager());

    }


    public String addSound(Sound sound) {
        if (repository.addSound(sound)) return "Ok";
        return "Can't add sound";
    }

    public List<Sound> getAllSounds() {
        return repository.getAllSounds();
    }


    public void showSoundList(IChannel channel) {

        StringBuilder sb = new StringBuilder();
        List<Sound> sounds = repository.getAllSounds();
        sb.append("**Cписок звуков:**\n");
        sb.append("```Markdown\n");
        for (Sound sound : sounds) {
            sb.append("* " + sound.name + "\n");
        }
        sb.append("```");

        discord.sendMessage(sb.toString(), channel);
    }

    public void playSound(List<String> commandParams, IMessage message) {
        playSound(message.getGuild(),message.getAuthor().getConnectedVoiceChannels().get(0),commandParams.get(0));
    }

    public void playSound(IGuild guild, IVoiceChannel chnl, String soundId){

        Sound sound = repository.getSound(soundId);
        IVoiceChannel channel = chnl;

        AudioPlayer player = playerManager.createPlayer();
        player.setVolume(50);
        trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        playerManager.loadItem(sound.url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                trackScheduler.queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    trackScheduler.queue(track);
                }
            }

            @Override
            public void noMatches() {
                System.out.println("noMatches()");
            }

            @Override
            public void loadFailed(FriendlyException e) {
                System.out.println("loadFailed(FriendlyException e)");
            }

        });
        try {
            channel.join();
            IAudioProvider provider = new LavaAudioProvider(player);
            guild.getAudioManager().setAudioProvider(provider);
            trackScheduler.nextTrack();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        }
    }


    public void stopSound(IMessage message) {
        trackScheduler.nextTrack();
        discord.leaveAllVoiceChannels();
    }


}
