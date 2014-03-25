package partybutler.song.info;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 22.03.2014.
 */
public class Artist {

    private String name;
    private File artistImage;

    private ArrayList<Album> albums;

    public Artist(String name) {
        this.name = name;
        albums = new ArrayList<Album>();
    }

    public Artist(String name, File artistImage) {
        this.name = name;
        this.artistImage = artistImage;
        albums = new ArrayList<Album>();
    }

    public void loadArtistImage() {
        //TODO: Check if image already Cached, else load via lastfm api
    }

    public void addAlbums(ArrayList<Album> newAlbums) {
        for (Album newAlbum : newAlbums) {
            if (!albums.contains(newAlbum)) {
                albums.add(newAlbum);
            } else {
                for (Album existingAlbum : albums) {
                    if (existingAlbum.equals(newAlbum)) {
                        existingAlbum.merge(newAlbum);
                    }
                }
            }
        }
    }

    public void addAlbum(Album newAlbum) {
        if (!albums.contains(newAlbum)) {
            albums.add(newAlbum);
        } else {
            for (Album existingAlbum : albums) {
                if (existingAlbum.equals(newAlbum)) {
                    existingAlbum.merge(newAlbum);
                }
            }
        }
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public String getName() {
        return name;
    }

    public File getArtistImage() {
        if (artistImage == null || !artistImage.canRead()) {
            artistImage = new File("data/placeholder.jpg");
        }

        return artistImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (!name.equalsIgnoreCase(artist.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Merges this artist with the other Artist
     *
     * @param newArtist - Other artist
     */
    public void merge(Artist newArtist) {
        addAlbums(newArtist.getAlbums());
    }

    @Override
    public String toString() {
        return name + " - Album Count: " + albums.size();
    }
}
