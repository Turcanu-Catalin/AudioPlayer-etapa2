package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import fileio.input.SongInput;

import java.util.List;

public class Album extends AudioCollection{
    private String albumName;
    private int releaseYear;
    private String description;
    private List<Song> songs;
    private String albumOwner;

    public Album(String albumName,int releaseYear,String description,List<Song> songs,String albumOwner){
        super(albumName,albumOwner);
        this.albumName = albumName;
        this.albumOwner = albumOwner;
        this.releaseYear = releaseYear;
        this.songs = songs;
        this.description = description;
    }

    public void setName(String name) {
        this.albumName = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getAlbumOwner() {
        return albumOwner;
    }

    public void setAlbumOwner(String albumOwner) {
        this.albumOwner = albumOwner;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(int index) {
        return songs.get(index);
    }

    @Override
    public boolean matchesDescription(String description) { return this.getDescription().equals(description); }

    @Override
    public boolean matchesOwner(String user) {
        return this.getAlbumOwner().equals(user);
    }

    @Override
    public boolean isAlbum(){return true;}



}