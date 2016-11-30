package kotik.simple.service.sound;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import sx.blah.discord.handle.audio.IAudioProvider;

/**
 * Created by Roman_Kuznetcov on 30.11.2016.
 */
public class LavaAudioProvider implements IAudioProvider {

    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    public LavaAudioProvider(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public boolean isReady() {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public byte[] provide() {
        return lastFrame.data;
    }

    @Override
    public AudioEncodingType getAudioEncodingType() {
        return AudioEncodingType.OPUS;
    }
}
