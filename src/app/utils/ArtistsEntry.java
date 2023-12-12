package app.utils;

import app.audio.Collections.Album;
import app.audio.LibraryEntry;
import lombok.Getter;

import java.util.ArrayList;

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

    public ArtistsEntry(final String artistName, final int age,
                        final String city, final ArrayList<Album> albums,
                        final ArrayList<Event> events, final ArrayList<Merch> merches) {
        super(artistName);
        this.artistName = artistName;
        this.age = age;
        this.city = city;
        this.albums = albums;
        this.events = events;
        this.merches = merches;
    }

    /**
     * Indicates whether this entry represents an artist.
     *
     * @return true since this entry represents an artist.
     */
    @Override
    public boolean isArtistEntry() {
        return true;
    }
}
