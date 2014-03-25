package partybutler.player.files.settings;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import partybutler.Constants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 25.03.2014.
 */
public class SettingsIO {

    public SettingsIO() {
    }

    public JsonObject readArtists() {
        return readJSON(Constants.LIBRARY_FILE_PATH);
    }

    public void writeConfigs(JsonObject configs){
        writeJSON(configs, Constants.CONFIGS_FILE_PATH);
    }

    public JsonObject readConfigs(){
        return readJSON(Constants.CONFIGS_FILE_PATH);
    }

    public void writeArtists(JsonObject artists) {
        writeJSON(artists, Constants.LIBRARY_FILE_PATH);
    }

    private void writeJSON(JsonObject object, String path){
        try {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            Logger.getRootLogger().debug("Writing JSON to File to: "+path);
            FileWriter writer = new FileWriter(path);
            writer.write(json);
            writer.flush();
            writer.close();
            Logger.getRootLogger().debug("Writing JSON Done.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject readJSON(String path){
        try {
            JsonParser parse = new JsonParser();
            JsonElement element = parse.parse(new FileReader(Constants.CONFIGS_FILE_PATH));
            return element.getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
