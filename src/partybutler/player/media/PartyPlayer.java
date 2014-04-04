package partybutler.player.media;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import partybutler.player.files.interfaces.MediaFile;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 22.03.2014.
 */

public class PartyPlayer {
    private MediaFile mediaFile = null;
    private BasicPlayer player = null;
    private static PartyPlayer instance = new PartyPlayer();
    private BasicPlayerListener playBackListener;

    private PartyPlayer() {
        player = new BasicPlayer();
    }

    /**
     * Sets the MediaFile  @param mediaFile - The Media File to play
     */
    public void setMediaFile(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }

    /**
     * Adds the @PlaybackListener to the player
     *
     * @param listener
     */
    public void setBasicPlayerListener(BasicPlayerListener listener) {
        playBackListener = listener;
        player.addBasicPlayerListener(playBackListener);
    }

    /**
     * Starts the music if the media file has been set
     *
     * @return True if the music could be started, false otherwise
     */
    public boolean start() {

        if (player != null && mediaFile != null && mediaFile.isReadable()) {
            try {
                player.open(AudioSystem.getAudioInputStream(mediaFile.getMediaInputStream()));
                player.play();
                return true;
            } catch (BasicPlayerException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Stops the media playback
     */
    public void stop() {
        if (player != null) {
            try {
                player.stop();
            } catch (BasicPlayerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pauses the playback
     */
    public void pause() {
        if (player != null) {
            try {
                player.pause();
            } catch (BasicPlayerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the Volume
     *
     * @param volume int value between 0 and 100
     */
    public void setVolume(int volume) {
        if (player != null && volume >= 0 && volume <= 100) {
            try {
                player.setGain(((double) volume) / 100);
            } catch (BasicPlayerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if music is playing
     * @return true if music is playing, false otherwise
     */
    public boolean isRunning() {
        return player.getStatus() == BasicPlayer.PLAYING;
    }

    /**
     * Gets the current partybutler.player.media.PartyPlayer instance
     *
     * @return instance of @partybutler.player.media.PartyPlayer
     */
    public static PartyPlayer getInstance() {
        return instance;
    }
}
