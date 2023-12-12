package app.utils;

import app.audio.Collections.Podcast;
import app.audio.LibraryEntry;
import lombok.Getter;

import java.util.ArrayList;

public class HostEntry extends LibraryEntry {
    @Getter
    private String hostName;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Podcast> podcasts;
    @Getter
    private ArrayList<Announcement> announcements;



    public HostEntry(final String hostName, final int age, final String city,
                     final ArrayList<Podcast> podcasts,
                     final ArrayList<Announcement> announcements) {
        super(hostName);
        this.hostName = hostName;
        this.age = age;
        this.city = city;
        this.podcasts = podcasts;
        this.announcements = announcements;
    }

    /**
     * This class represents a host entry, indicating that the user is a host.
     */
    @Override
    public boolean isHostEntry() {
        return true;
    }
}
