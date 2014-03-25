package partybutler.player.files;

import partybutler.Constants;
import partybutler.player.files.settings.ConfigManager;
import partybutler.player.files.settings.SettingsManager;
import partybutler.song.info.Artist;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 22.03.2014.
 */
public class FileScanner {

    private static FileScanner instance = new FileScanner();

    private boolean monitoring = false;
    private Timer timer;

    private FileScanner(){
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            checkDirectories();      
        }
    };

    private void checkDirectories() {
        for(File directory : ConfigManager.getInstance().getWatchedDirectories()){
            if(directory.canRead()){
                try {
                    Files.walkFileTree(directory.toPath(),new FileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            if(attrs.isRegularFile()){
                                File currentFile = file.toFile();
                                if(currentFile.getName().toLowerCase().endsWith(".mp3")) {
                                    Artist artist = MediaInfoExtractor.getInstance().extractMediaInfo(currentFile);
                                    if (artist != null) {
                                        Library.getInstance().addArtist(artist);
                                    }
                                }
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            //Save the Library
                            SettingsManager.getInstance().saveLibraryToFile();

                            return FileVisitResult.CONTINUE;
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startMonitoring(){
        monitoring = true;
        timer = new Timer("DirectoryMonitorTimer");
        int delay = Constants.MONITOR_RATE_IN_SECONDS*1000;
        //TODO: CHANGE BACK TO DELAYS
        timer.scheduleAtFixedRate(timerTask, 0, Constants.MONITOR_RATE_IN_SECONDS*1000);
    }

    public void stopMonitoring(){
        monitoring = false;
        timer.cancel();
    }

    public static FileScanner getInstance() {
        return instance;
    }
}
