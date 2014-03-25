package partybutler.player.files.web.youtube;

import org.apache.log4j.Logger;
import partybutler.Constants;
import partybutler.player.files.Library;
import partybutler.player.files.mediatypes.Mp3MediaFile;
import partybutler.song.info.Album;
import partybutler.song.info.Artist;

/**
 * Created by Administrator on 23.03.2014.
 */
public class YoutubeManager {

    private static YoutubeManager instance = new YoutubeManager();

    private YoutubeManager(){

    }

    public void addYoutubeToLibrary(String youtubeUrl){
        final YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
        Logger.getRootLogger().debug("Initializing Youtube download of Video: "+youtubeUrl);
        youtubeDownloader.initializeDownloader(youtubeUrl, new Runnable() {
            @Override
            public void run() {
                Logger.getRootLogger().debug("Adding Youtube Media to Library");
                Mp3MediaFile mediaFile = new Mp3MediaFile(youtubeDownloader.getOutputFile(),youtubeDownloader.getTitle());
                Logger.getRootLogger().debug("Media File: "+youtubeDownloader.getOutputFile().getAbsolutePath());
                Album album = new Album(Constants.UNKNOWN_ALBUM);
                album.addMediaFile(mediaFile);
                Artist artist = new Artist(Constants.YOUTUBE_ARTIST);
                artist.addAlbum(album);
                Logger.getRootLogger().debug("Adding Downloaded Youtube Media to Library");
                Library.getInstance().addArtist(artist);
            }
        });

        Thread downloaderThread = new Thread(youtubeDownloader);
        downloaderThread.start();
    }



    public static YoutubeManager getInstance() {
        return instance;
    }
}
