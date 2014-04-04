package partybutler.player;

import partybutler.Constants;
import partybutler.player.files.interfaces.MediaFile;
import partybutler.song.info.Album;
import partybutler.song.info.Artist;

/**
 * Created by Administrator on 26.03.2014.
 */
public class CurrentSongInformation {

    private static CurrentSongInformation instance = new CurrentSongInformation();

    private Artist artist;
    private Album album;
    private MediaFile mediaFile;

    private CurrentSongInformation() {
    }

    public synchronized Artist getArtist() {
        return artist;
    }

    public synchronized Album getAlbum() {
        return album;
    }

    public synchronized MediaFile getNowPlaying() {
        return mediaFile;
    }

    public synchronized String getNowPlayingText(){
        if(artist != null && album != null && mediaFile != null){
            return artist.getName() +" - " +mediaFile.getTitle();
        } else {
            return Constants.NO_SONG_PLAYING;
        }
    }

    public synchronized void setNowPlaying(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
        if(mediaFile != null) {
            this.album = mediaFile.getAlbum();
            this.artist = mediaFile.getArtist();
        } else {
            this.album = null;
            this.artist = null;
        }
    }

    public static CurrentSongInformation getInstance() {
        return instance;
    }
}
