package app.utils;

import app.audio.Collections.Album;
import app.audio.LibraryEntry;
import app.user.Artist;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ArtistsEntry extends LibraryEntry {
    @Getter
    private String artistName;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Event> events;
    @Getter
    private ArrayList<Merch> merches;

    public ArtistsEntry(String artistName, int age, String city, ArrayList<Album> albums, ArrayList<Event> events, ArrayList<Merch> merches) {
        super(artistName);
        this.artistName = artistName;
        this.age = age;
        this.city = city;
        this.albums = albums;
        this.events = events;
        this.merches = merches;
    }

    @Override
    public boolean isArtistEntry(){
        return true;
    }
}
