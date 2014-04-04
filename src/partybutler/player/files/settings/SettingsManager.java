package partybutler.player.files.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import partybutler.Constants;
import partybutler.player.files.Library;
import partybutler.player.files.interfaces.MediaFile;
import partybutler.player.files.mediatypes.Mp3MediaFile;
import partybutler.song.info.Album;
import partybutler.song.info.Artist;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 25.03.2014.
 */
public class SettingsManager {
    private static SettingsManager instance = new SettingsManager();


    private SettingsManager() {
    }

    public void writeConfigsToFile() {
        JsonObject configObject = ConfigManager.getInstance().getConfigsAsJSON();
        SettingsIO io = new SettingsIO();
        io.writeConfigs(configObject);
    }

    public void readConfigsFromFile() {
        SettingsIO io = new SettingsIO();
        JsonObject configObject = io.readConfigs();

        if (configObject != null) {
            JsonArray directoryArray = configObject.getAsJsonArray(Constants.WATCHEDDIRECTORYARRAYNAME);
            for (JsonElement jsonElement : directoryArray) {
                JsonObject directory = jsonElement.getAsJsonObject();
                ConfigManager.getInstance().addWatchedDirectory(new File(directory.get(Constants.WATCHEDDIRECTORYPATHFIELD).getAsString()));
            }
        }
    }

    public void readLibraryFromFile() throws Exception {
        SettingsIO io = new SettingsIO();
        JsonObject object = io.readArtists();

        if (object != null) {
            ArrayList<Artist> artists = extractArtists(object);
            Library library = Library.getInstance();
            for (Artist artist : artists) {
                library.addArtist(artist);
            }
        }
    }

    private ArrayList<Artist> extractArtists(JsonObject libraryJsonObject) throws Exception {
        ArrayList<Artist> artistArrayList = new ArrayList<Artist>();

        JsonElement element = libraryJsonObject.get(Constants.ARTISTARRAYNAME);

        JsonArray artistArray = element.getAsJsonArray();
        Iterator<JsonElement> iterator = artistArray.iterator();
        while (iterator.hasNext()) {
            JsonObject artist = iterator.next().getAsJsonObject();
            String artistName = artist.get(Constants.ARTISTNAMEFIELD).getAsString();
            File artistArtFile = new File(artist.get(Constants.ARTISTARTFIELD).getAsString());

            Artist artistObject = new Artist(artistName, artistArtFile);
            artistObject.addAlbums(extractAlbums(artist, artistObject));
            artistArrayList.add(artistObject);
        }

        return artistArrayList;
    }

    private ArrayList<Album> extractAlbums(JsonObject artist, Artist artistObject) throws Exception {
        ArrayList<Album> albumArrayList = new ArrayList<Album>();

        JsonArray albums = artist.getAsJsonArray(Constants.ARTISTALBUMSARRAYNAME);

        for (JsonElement album : albums) {
            JsonObject albumObject = album.getAsJsonObject();
            String albumName = albumObject.get(Constants.ALBUMNAMEFIELD).getAsString();
            File albumArtFile = new File(albumObject.get(Constants.ALBUMARTFIELD).getAsString());

            Album albumEntry = new Album(albumName, artistObject , albumArtFile);
            albumEntry.addMediaFiles(extractSongs(albumObject, albumEntry, artistObject));
            albumArrayList.add(albumEntry);
        }
        return albumArrayList;
    }

    private ArrayList<MediaFile> extractSongs(JsonObject albumObject, Album albumEntry, Artist artistObject) throws Exception {
        ArrayList<MediaFile> mediaFileArrayList = new ArrayList<MediaFile>();

        JsonArray songs = albumObject.getAsJsonArray(Constants.ALBUMSONGSARRAYNAME);

        for (JsonElement song : songs) {
            JsonObject songObject = song.getAsJsonObject();
            String title = songObject.get(Constants.SONGTITLEFIELD).getAsString();
            File mediaPath = new File(songObject.get(Constants.SONGPATHFIELD).getAsString());
            Integer mediaType = songObject.get(Constants.SONGTYPEFIELD).getAsInt();
            MediaFile file = null;
            switch (mediaType.intValue()) {
                case Constants.TYPE_MP3:
                    file = new Mp3MediaFile(mediaPath,artistObject,albumEntry, title);
                    break;
                default:
                    throw new Exception("Media Type not Covered. Fix this!");
            }
            mediaFileArrayList.add(file);
        }

        return mediaFileArrayList;
    }

    public void saveLibraryToFile() {
        ArrayList<Artist> artists = Library.getInstance().getArtists();
        JsonObject object = convertArtistsToJsonObject(artists);
        SettingsIO io = new SettingsIO();
        io.writeArtists(object);
    }

    private JsonObject convertArtistsToJsonObject(ArrayList<Artist> artists) {
        JsonObject object = new JsonObject();
        JsonArray artistArray = new JsonArray();

        for (Artist artist : artists) {
            JsonObject artistObject = new JsonObject();
            artistObject.addProperty(Constants.ARTISTNAMEFIELD, artist.getName());
            artistObject.addProperty(Constants.ARTISTARTFIELD, artist.getArtistImage().getAbsolutePath());
            artistObject.add(Constants.ARTISTALBUMSARRAYNAME, convertAlbumsToJsonObject(artist.getAlbums()));
            artistArray.add(artistObject);
        }

        object.add(Constants.ARTISTARRAYNAME, artistArray);

        return object;
    }

    private JsonArray convertAlbumsToJsonObject(ArrayList<Album> albums) {
        JsonArray albumArray = new JsonArray();

        for (Album album : albums) {
            JsonObject albumObject = new JsonObject();
            albumObject.addProperty(Constants.ALBUMNAMEFIELD, album.getName());
            albumObject.addProperty(Constants.ALBUMARTFIELD, album.getAlbumArt().getAbsolutePath());
            albumObject.add(Constants.ALBUMSONGSARRAYNAME, convertSongsToJsonObject(album.getMediaFiles()));
            albumArray.add(albumObject);
        }


        return albumArray;
    }

    private JsonArray convertSongsToJsonObject(ArrayList<MediaFile> mediaFiles) {
        JsonArray songArray = new JsonArray();

        for (MediaFile file : mediaFiles) {
            JsonObject songObject = new JsonObject();
            songObject.addProperty(Constants.SONGTITLEFIELD, file.getTitle());
            songObject.addProperty(Constants.SONGPATHFIELD, file.getMediaPath());
            songObject.addProperty(Constants.SONGTYPEFIELD, file.getMediaType());

            songArray.add(songObject);
        }

        return songArray;
    }

    public static SettingsManager getInstance() {
        return instance;
    }
}
