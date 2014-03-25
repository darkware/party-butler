package partybutler.player.files.mediatypes;

import partybutler.Constants;
import partybutler.player.files.interfaces.MediaFile;

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

    public Mp3MediaFile(File mediaFile, String title) {
        this.mediaFile = mediaFile;
        this.title = title;
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
    public boolean equals(MediaFile o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (!title.equals(o.getTitle())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
