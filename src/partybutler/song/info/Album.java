package partybutler.song.info;

import partybutler.player.files.interfaces.MediaFile;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 22.03.2014.
 */
public class Album {

    private String name;
    private File albumArt;
    private ArrayList<MediaFile> mediaFiles;


    public Album(String name) {
        this.name = name;
        mediaFiles = new ArrayList<MediaFile>();
    }

    public Album(String name, File albumArt) {
        this.name = name;
        this.albumArt = albumArt;
        mediaFiles = new ArrayList<MediaFile>();
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
}
