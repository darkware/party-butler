package partybutler;

/**
 * Created by Administrator on 22.03.2014.
 */
public class Constants {

    public static final String UNKNOWN_ARTIST = "Unknown Artist";
    public static final String UNKNOWN_ALBUM = "Unknown Album";

    public static final String YOUTUBE_ARTIST = "Youtube Videos";

    public static final String ALBUM_ONLINE = "Internet Media";

    public static final int TYPE_MP3 = 0;
    public static final int TYPE_YOUTUBE = 1;
    public static final int TYPE_GROOVESHARK = 2;

    public static final int MONITOR_RATE_IN_SECONDS = 600;

    public static final String YOUTUBE_DOWNLOAD_LINK = "http://localhost:8080/party-butler-server-exploded/youtube?url=";

    public static final String LOGGER_NAME = "partyLogger";

    /**
     * JSON Attribute Names
     */

    public static final String ARTISTARRAYNAME = "artists";
    public static final String ARTISTNAMEFIELD = "name";
    public static final String ARTISTARTFIELD = "artistart";
    public static final String ARTISTALBUMSARRAYNAME = "albums";

    public static final String ALBUMNAMEFIELD = "albumname";
    public static final String ALBUMARTFIELD = "albumart";
    public static final String ALBUMSONGSARRAYNAME = "songs";

    public static final String SONGTITLEFIELD = "title";
    public static final String SONGPATHFIELD = "path";
    public static final String SONGTYPEFIELD = "type";


    public static final String WATCHEDDIRECTORYARRAYNAME = "directories";
    public static final String WATCHEDDIRECTORYPATHFIELD = "watchedpath";



    public static final String LIBRARY_FILE_PATH = "data/conf/library.ini";
    public static final String CONFIGS_FILE_PATH = "data/conf/configs.ini";
    public static final String APPLICATION_NAME = "Party Butler";

    public static final String ALBUM_ALL = "<All Albums>";
    public static final String SONG_ALL = "<All Songs>";

    public static final int LISTENER_ACTION_ID_ENQUEUED_FIRST = 1;
    public static final int LISTENER_ACTION_ID_ENQUEUED_LAST = 2;

    /*
    UI Constants
     */

    public static final String VOLUME_TEXT = "Volume: ";
    public static final String NO_ARTIST_TEXT = "No artist selected.";
    public static final String NO_ALBUM_TEXT = "No album selected.";
    public static final String NO_TITLE_TEXT = "No song selected.";

    public static final String PLACEHOLDER_IMAGE_PATH = "data\\placeholder.jpg";

    public static final int LISTTYPE_ARTIST = 0;
    public static final int LISTTYPE_ALBUM = 1;
    public static final int LISTTYPE_COVERFLOW = 2;
    public static final int LISTTYPE_TITLE = 3;

    public static final String NO_SONG_PLAYING = "No song playing.";
}
