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



    public HostEntry(String hostName, int age, String city, ArrayList<Podcast> podcasts, ArrayList<Announcement> announcements) {
        super(hostName);
        this.hostName = hostName;
        this.age = age;
        this.city = city;
        this.podcasts = podcasts;
        this.announcements = announcements;
    }

    @Override
    public boolean isHostEntry(){
        return true;
    }
}
