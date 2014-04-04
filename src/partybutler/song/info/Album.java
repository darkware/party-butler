package partybutler.song.info;

import partybutler.Constants;
import partybutler.player.files.interfaces.MediaFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 22.03.2014.
 */
public class Album {

    private String name;
    private File albumArt;
    private ArrayList<MediaFile> mediaFiles;
    private BufferedImage albumImage;
    private Artist artist;

    public Album(String name, Artist artist) {
        this.name = name;
        this.artist = artist;
        mediaFiles = new ArrayList<MediaFile>();
        try {
            albumImage = ImageIO.read(new File(Constants.PLACEHOLDER_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Album(String name, Artist artist, File albumArt) {
        this.name = name;
        this.artist = artist;
        this.albumArt = albumArt;
        mediaFiles = new ArrayList<MediaFile>();
        try {
            albumImage = ImageIO.read(albumArt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAlbumArt(){
        //TODO: Check if image already Cached, else load via lastfm api
    }

    public void addMediaFiles(ArrayList<MediaFile> newMediaFiles){
        for(MediaFile newMediaFile : newMediaFiles){
            if(!mediaFiles.contains(newMediaFile)){
                mediaFiles.add(newMediaFile);
            }
        }
    }

    public void addMediaFile(MediaFile file){
        if(!mediaFiles.contains(file)){
            mediaFiles.add(file);
        }
    }

    public ArrayList<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public int getSongCount(){
        return mediaFiles.size();
    }

    public String getName() {
        return name;
    }

    public File getAlbumArt() {
        if(albumArt == null || !albumArt.canRead()){
            albumArt = new File("data/placeholder.jpg");
        }
        return albumArt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (!name.equals(album.name)) return false;

        return true;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Merges this album with other album
     * @param newAlbum - other album
     */
    public void merge(Album newAlbum) {
        addMediaFiles(newAlbum.getMediaFiles());
    }

    public BufferedImage getImage() {
       return albumImage;
    }

    @Override
    public String toString() {
        return name;
    }
}
