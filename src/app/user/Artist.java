package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.utils.Date;
import app.utils.Event;
import app.utils.Merch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Artist extends User {

    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Event> events;
    @Getter
    private ArrayList<Merch> merches;

    public Artist(String username, int age, String city) {
        super(username, age, city);
        albums = new ArrayList<>();
        events = new ArrayList<>();
        merches = new ArrayList<>();
        this.setUserType("artist");
    }

    public  Album getAlbum(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    public String addAlbum(String name, int releaseYear, String description, List<Song> songs, String owner){
        Album album = getAlbum(name);
        if(albums.contains(album)){
            return getUsername() + " has another album with the same name.";
        } else {
            Set<String> uniqueSongNames = new HashSet<>();
            for (Song song : songs) {
                if (!uniqueSongNames.add(song.getName())) {
                    return getUsername() + " has the same song at least twice in this album.";
                }
            }
            Album newAlbum = new Album(name, releaseYear, description, songs,owner) ;
            albums.add(newAlbum);
            Admin.addAlbumSongs(newAlbum);
            return getUsername() + " has added new album successfully.";
        }
    }

    public String addEvent(String name, String description, Date date){
        if (hasEvent(name)) {
            return getUsername() + " has another event with the same name.";
        }

        if (!isValidDate(date)) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }

        Event newEvent = new Event(name, description, date);
        events.add(newEvent);

        return getUsername() + " has added new event successfully.";

    }

    public String addMerch(String name, String description, int price){
        if (hasMerch(name)) {
            return getUsername() + " has merchandise with the same name.";
        }

        if(price < 0){
            return "Price for merchandise can not be negative.";
        }

        Merch newMerch = new Merch(name, description, price);
        merches.add(newMerch);

        return getUsername() + " has added new merchandise successfully.";

    }

    public String removeAlbum(String name){
        Album album = getAlbum(name);
        if(hasAlbum(name)){
            List<User> users = Admin.getUsers();
            for(User user : users){
                Album sourceAlbum = new Album(",", 0, "", null, "");
                if (user.getPlayer().getSource() != null) {
                    sourceAlbum = (Album) user.getPlayer().getSource().getAudioCollection();
                }
                if(sourceAlbum.getName().equals(name)){
                    return getUsername() + " can't delete this album.";
                }
            }

            albums.remove(album);
            Admin.deleteAlbumSongs(album);
            return getUsername() + " deleted the album successfully.";

        } else {
            return getUsername() + " doesn't have an album with the given name.";
        }
    }

    public boolean hasEvent(String name){
        for(Event event : getEvents()){
            if(event.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean isValidDate(Date date){
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();

        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900 || year > 2023) {
            return false;
        }

        if (month == 2 && day > 28) {
            return false;
        }

        return true;
    }

    public Date parseDate(String dateString) {
        String[] dateParts = dateString.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        return new Date(day, month, year);
    }

    public boolean hasMerch(String name){
        for(Merch merch : merches){
            if(merch.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean hasAlbum(String name){
        for(Album album : albums){
            if(album.getName().equals(name)){
                return true;
            }
        }
        return false;
    }


}
