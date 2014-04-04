package partybutler.player.files.interfaces;

import partybutler.song.info.Album;
import partybutler.song.info.Artist;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Administrator on 22.03.2014.
 */
public interface MediaFile {
    /**
     * Gets the Input stream from the Media File
     * @return @InputStream
     */
    public InputStream getMediaInputStream() throws FileNotFoundException;

    /**
     * Checks whether the Media File is accessible (URL Available, File readable etc.)
     * @return true if MediaFile is readable, false otherwise
     */
    public boolean isReadable();

    /**
     * Gets the title from the Media File
     * @return @String MediaTitle
     */
    public String getTitle();

    /**
     * Gets the path to the Media (Local file path, URL etc.)
     * @return Path in String form
     */
    public String getMediaPath();

    /**
     * Returns the Media Type of the Media File (See {@link partybutler.Constants})
     * @return int value of MediaType
     */
    public int getMediaType();

    /**
     * Gets the Artist of the Song
     * @return Artist of the Media File
     */
    public Artist getArtist();

    /**
     * Gets the Album the Song is on
     * @return Album the Song is on
     */
    public Album getAlbum();

    public boolean equals(Object other);

    public String toString();
}
