package partybutler.player.files.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import partybutler.Constants;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 25.03.2014.
 */
public class ConfigManager {

    private static ConfigManager instance = new ConfigManager();

    private ConfigManager() {
    }

    private ArrayList<File> watchedDirectories = new ArrayList<File>();

    public ArrayList<File> getWatchedDirectories() {
        return watchedDirectories;
    }

    public void setWatchedDirectories(ArrayList<File> watchedDirectories) {
        this.watchedDirectories = watchedDirectories;
    }

    public void addWatchedDirectory(File directory) {
        if(directory != null && directory.canRead() && directory.isDirectory() && !watchedDirectories.contains(directory)) {
            watchedDirectories.add(directory);
        }
    }

    public void addWatchedDirectoryAndSave(File directory) {
        if(directory != null && directory.canRead() && directory.isDirectory() && !watchedDirectories.contains(directory)) {
            watchedDirectories.add(directory);
        }
        SettingsManager.getInstance().writeConfigsToFile();
    }

    public void removeWatchedDirectory(File directory) {
        watchedDirectories.remove(directory);
        SettingsManager.getInstance().writeConfigsToFile();
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    public JsonObject getConfigsAsJSON(){
        JsonObject object = new JsonObject();
        JsonArray watchedDirectoryArray = new JsonArray();

        for(File directory : watchedDirectories){
            JsonObject directoryObject = new JsonObject();
            directoryObject.addProperty(Constants.WATCHEDDIRECTORYPATHFIELD,directory.getAbsolutePath());
            watchedDirectoryArray.add(directoryObject);
        }
        object.add(Constants.WATCHEDDIRECTORYARRAYNAME, watchedDirectoryArray);
        return object;
    }
}
