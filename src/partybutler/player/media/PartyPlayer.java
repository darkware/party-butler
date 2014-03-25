package partybutler.player.media;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;
import partybutler.player.files.interfaces.MediaFile;

import java.io.IOException;

/**
 * Created by Administrator on 22.03.2014.
 */

public class PartyPlayer {
    private MediaFile mediaFile = null;
    private AdvancedPlayer player = null;
    private static PartyPlayer instance = new PartyPlayer();
    private PlaybackListener playBackListener;

    private PartyPlayer() {
    }

    /**
     * Sets the MediaFile  @param mediaFile - The Media File to play
     */
    public void setMediaFile(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }

    /**
     * Initializes the player
     */
    private void initializePlayer() {
        if (mediaFile != null && mediaFile.isReadable()) {
            try {
                player = new AdvancedPlayer(mediaFile.getMediaInputStream());
                if(playBackListener != null){
                    player.setPlayBackListener(playBackListener);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds the @PlaybackListener to the player
     * @param listener
     */
    public void setPlaybackListener(PlaybackListener listener){
        playBackListener = listener;
    }

    /**
     * Starts the music if the media file has been set
     * @return True if the music could be started, false otherwise
     */
    public boolean start() {
        initializePlayer();

        if (player != null) {
            try {
                player.play();
                return true;
            } catch (JavaLayerException e) {
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
            player.close();
        }
        player = null;
    }

    /**
     * Gets the current partybutler.player.media.PartyPlayer instance
     * @return instance of @partybutler.player.media.PartyPlayer
     */
    public static PartyPlayer getInstance() {
        return instance;
    }
}
