package partybutler.player.files;

import partybutler.song.info.Album;
import partybutler.song.info.Artist;

import java.util.ArrayList;

/**
 * Created by Administrator on 22.03.2014.
 */
public class Library {

    private static Library instance = new Library();
    private ArrayList<Artist> artists;

    private Library() {
        artists = new ArrayList<Artist>();
    }


    public synchronized void addArtist(Artist artist) {
        /* If artists doesn't exist yet, add him else find Artist and merge them */
        if (!artists.contains(artist)) {
            artists.add(artist);
        } else {
            for (Artist existingArtist : artists) {
                if (existingArtist.equals(artist)) {
                    existingArtist.merge(artist);
                }
            }
        }
    }

    public synchronized int getSongCount(){
        int count = 0;
        for(Artist artist : artists){
            for(Album album : artist.getAlbums()){
                count += album.getSongCount();
            }
        }

        return count;
    }

    public synchronized ArrayList<Artist> getArtists() {
        return artists;
    }

    public static Library getInstance() {
        return instance;
    }
}
