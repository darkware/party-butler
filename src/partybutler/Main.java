package partybutler;

import partybutler.player.files.FileScanner;
import partybutler.player.files.Library;
import partybutler.player.files.interfaces.MediaFile;
import partybutler.player.files.queue.PublicQueue;
import partybutler.player.files.settings.SettingsManager;
import partybutler.player.media.PartyPlayer;
import partybutler.song.info.Album;
import partybutler.song.info.Artist;

/**
 * Created by Administrator on 22.03.2014.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
       /* File mediafile = new File("D:\\Downloads\\");
        FileScanner scanner = FileScanner.getInstance();
        scanner.addDirectoryToWatch(mediafile);
        scanner.startMonitoring();*/

        //TODO: Change to real Configuration
        org.apache.log4j.BasicConfigurator.configure();

        /*ConfigManager.getInstance().addWatchedDirectory(new File("D:\\Downloads\\2000's Music\\"));

        FileScanner scanner = FileScanner.getInstance();
        scanner.startMonitoring();*/

        SettingsManager.getInstance().readConfigsFromFile();
        FileScanner scanner = FileScanner.getInstance();
        scanner.startMonitoring();

        while(Library.getInstance().getSongCount() < 5){
            Thread.sleep(1000);
        }

        PublicQueue queue = PublicQueue.getInstance();
        Library lib = Library.getInstance();
        for(Artist artist : lib.getArtists()){
            for(Album album : artist.getAlbums()){
                for(MediaFile file: album.getMediaFiles()){
                    queue.enqueue(file);
                }
            }
        }
        queue.getNextMediaFile();
        PartyPlayer.getInstance().setMediaFile(queue.getNextMediaFile());
        PartyPlayer.getInstance().start();

        /*try {
            SettingsManager.getInstance().readLibraryFromFile();
            PublicQueue queue = PublicQueue.getInstance();
            Library lib = Library.getInstance();
            for(Artist artist : lib.getArtists()){
                for(Album album : artist.getAlbums()){
                    for(MediaFile file: album.getMediaFiles()){
                        queue.enqueue(file);
                    }
                }
            }
            queue.getNextMediaFile();
            PartyPlayer.getInstance().setMediaFile(queue.getNextMediaFile());
            PartyPlayer.getInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*try {
            YoutubeManager.getInstance().addYoutubeToLibrary("http://www.youtube.com/watch?v=TjWmsHo_kvA");^
            Thread.sleep(5000);
            YoutubeManager.getInstance().addYoutubeToLibrary("http://www.youtube.com/watch?v=ThruPm_4oow");
            while(Library.getInstance().getSongCount() < 2){
                System.out.println("Waiting");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        PublicQueue queue = PublicQueue.getInstance();
        for(Artist artist : Library.getInstance().getArtists()){
            for(Album album: artist.getAlbums()){
                for(MediaFile file : album.getMediaFiles()){
                    queue.enqueue(file);
                }
            }
        }*/

        /*PublicQueue queue = PublicQueue.getInstance();

        Mp3MediaFile mediaFile = new Mp3MediaFile(new File("D:\\Downloads\\dsj_samples\\dsj_samples\\media\\audio.mp3"),"test");
        Mp3MediaFile mediaFile2 = new Mp3MediaFile(new File("D:\\Downloads\\2000's Music\\Jenny From the Block - Jennifer Lopez.mp3"),"test");

        queue.enqueue(mediaFile);
        Thread.sleep(1000);
        queue.enqueue(mediaFile2);

        Logger.getRootLogger().debug("Playing queued song: "+queue.getNextMediaTitle());
        PartyPlayer.getInstance().setMediaFile(queue.getNextMediaFile());
        PartyPlayer.getInstance().setPlaybackListener(new PlaybackListener() {
            @Override
            public void playbackFinished(PlaybackEvent playbackEvent) {
                MediaFile nextMediaFile = PublicQueue.getInstance().getNextMediaFile();
                if (nextMediaFile != null) {
                    PartyPlayer.getInstance().setMediaFile(nextMediaFile);
                    PartyPlayer.getInstance().start();
                }
                //super.playbackFinished(playbackEvent);
            }

            @Override
            public void playbackStarted(PlaybackEvent playbackEvent) {
                super.playbackStarted(playbackEvent);
            }
        });
        PartyPlayer.getInstance().start();*/

        /*SettingsIO io = new SettingsIO();
        JsonObject object = io.readArtists().getAsJsonObject();*/

    }


}
