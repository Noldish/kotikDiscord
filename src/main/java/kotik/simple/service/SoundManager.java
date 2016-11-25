package kotik.simple.service;

import kotik.simple.dao.RepositoryManager;
import kotik.simple.dao.objects.Sound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.handle.audio.IAudioManager;
import sx.blah.discord.handle.audio.IAudioProvider;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.audio.AudioPlayer;
import sx.blah.discord.util.audio.providers.URLProvider;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Roman_Kuznetcov on 25.11.2016.
 */
@Service
public class SoundManager {

    @Autowired
    private RepositoryManager repository;

    @Autowired
    private DiscordService discord;

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
        AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(message.getGuild());
        Sound sound = repository.getSound(commandParams.get(0));
        try {
            IVoiceChannel channel = message.getAuthor().getConnectedVoiceChannels().get(0);
            channel.join();
            URL url = new URL(sound.url);
            player.queue(url);
        } catch (UnsupportedAudioFileException | IOException | MissingPermissionsException e) {
            e.printStackTrace();
        }
    }

    public void stopSound(IMessage message) {
        AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(message.getGuild());
        player.clear();
    }


}
