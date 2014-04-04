package partybutler.player.files.mediatypes;

import partybutler.Constants;
import partybutler.player.files.interfaces.MediaFile;
import partybutler.song.info.Album;
import partybutler.song.info.Artist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Administrator on 22.03.2014.
 * This class Represents a Local MP3 File
 */
public class Mp3MediaFile implements MediaFile {

    private File mediaFile = null;
    private String title = null;
    private Artist artist = null;
    private Album album = null;



    public Mp3MediaFile(File mediaFile, Artist artist, Album album, String title) {
        this.mediaFile = mediaFile;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    @Override
    public InputStream getMediaInputStream() throws FileNotFoundException {
        return new FileInputStream(mediaFile);
    }


    @Override
    public boolean isReadable() {
        return mediaFile.canRead();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getMediaPath() {
        return mediaFile.getAbsolutePath();
    }

    @Override
    public int getMediaType() {
        return Constants.TYPE_MP3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mp3MediaFile mediaFile1 = (Mp3MediaFile) o;

        if (!album.equals(mediaFile1.album)) return false;
        if (!artist.equals(mediaFile1.artist)) return false;
        if (!mediaFile.equals(mediaFile1.mediaFile)) return false;
        if (!title.equals(mediaFile1.title)) return false;

        return true;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public int hashCode() {
        int result = mediaFile.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + artist.hashCode();
        result = 31 * result + album.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return title;
    }
}
