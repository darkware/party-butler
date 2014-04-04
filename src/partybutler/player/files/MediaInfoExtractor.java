package partybutler.player.files;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import partybutler.Constants;
import partybutler.player.files.mediatypes.Mp3MediaFile;
import partybutler.song.info.Album;
import partybutler.song.info.Artist;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 22.03.2014.
 */
public class MediaInfoExtractor {

    private static MediaInfoExtractor instance = new MediaInfoExtractor();

    public MediaInfoExtractor() {
    }

    /**
     * Extracts the ID3 Info from an Mp3MediaFile and Creates an Artist with the Album that contains the given Song (For Merging in the Library)
     *
     * @param mp3MediaFile - The File to the given mp3 file
     * @return - Artists with Proper ID3 Information containing the Album and the Song
     */
    public Artist extractMediaInfo(File mp3MediaFile) {
        String artist, title, album;
        StringBuffer buffer = new StringBuffer();
        try {
            MP3File mp3File = new MP3File(mp3MediaFile, MP3File.LOAD_ALL, true);
            if (mp3File.hasID3v2Tag()) {
                ID3v24Tag tag = mp3File.getID3v2TagAsv24();


                artist = tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
                title = tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
                album = tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);

                if (artist != null && !artist.isEmpty()) {
                    buffer.append(artist).append(" - ");
                } else {
                    artist = Constants.UNKNOWN_ARTIST;
                }
                if (title != null && !title.isEmpty()) {
                    buffer.append(title);
                } else {
                    title = mp3MediaFile.getName();
                }
                if (album == null || album.isEmpty()) {
                    album = Constants.UNKNOWN_ALBUM;
                }
            } else if (mp3File.hasID3v1Tag()) {
                ID3v1Tag tag = mp3File.getID3v1Tag();

                artist = tag.getFirstArtist();
                title = tag.getFirstTitle();
                album = tag.getFirstAlbum();

                if (artist != null && !artist.isEmpty()) {
                    buffer.append(artist).append(" - ");
                } else {
                    artist = Constants.UNKNOWN_ARTIST;
                }
                if (title != null && !title.isEmpty()) {
                    buffer.append(title);
                } else {
                    title = mp3MediaFile.getName();
                }

                if (album == null || album.isEmpty()) {
                    album = Constants.UNKNOWN_ALBUM;
                }

            } else {
                artist = Constants.UNKNOWN_ARTIST;
                album = Constants.UNKNOWN_ALBUM;
                title = mp3MediaFile.getName();
            }

            if (title.contains(".mp3")) {
                title = title.replaceAll(".mp3", "");
            }
            Artist newArtist = new Artist(artist);
            Album newAlbum = new Album(album, newArtist);
            Mp3MediaFile mediaFile = new Mp3MediaFile(mp3MediaFile, newArtist, newAlbum, title);
            newAlbum.addMediaFile(mediaFile);
            newArtist.addAlbum(newAlbum);

            return newArtist;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static MediaInfoExtractor getInstance() {
        return instance;
    }
}
