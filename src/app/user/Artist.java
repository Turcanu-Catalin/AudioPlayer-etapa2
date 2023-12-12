package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Song;
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
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2023;
    private static final int FEBRUARY_NR = 2;
    private static final int MAX_FEBRUARY_DAY = 28;

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        albums = new ArrayList<>();
        events = new ArrayList<>();
        merches = new ArrayList<>();
        this.setUserType("artist");
    }

    /**
     * Retrieves the album with the specified name.
     *
     * @param name The name of the album.
     * @return The album with the specified name, or null if not found.
     */
    public Album getAlbum(final String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Retrieves the event with the specified name.
     *
     * @param name The name of the event.
     * @return The event with the specified name, or null if not found.
     */
    public Event getEvent(final String name) {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Adds a new album with the specified details to the artist's collection.
     *
     * @param name        The name of the album.
     * @param releaseYear The release year of the album.
     * @param description The description of the album.
     * @param songs       The list of songs in the album.
     * @param owner       The owner of the album.
     * @return A status message indicating the success or failure of the operation.
     */
    public String addAlbum(final String name, final int releaseYear,
                           final String description, final List<Song> songs, final String owner) {
        Album album = getAlbum(name);
        if (albums.contains(album)) {
            return getUsername() + " has another album with the same name.";
        } else {
            Set<String> uniqueSongNames = new HashSet<>();
            for (Song song : songs) {
                if (!uniqueSongNames.add(song.getName())) {
                    return getUsername() + " has the same song at least twice in this album.";
                }
            }
            Album newAlbum = new Album(name, releaseYear, description, songs, owner);
            albums.add(newAlbum);
            Admin.addAlbumSongs(newAlbum);
            return getUsername() + " has added new album successfully.";
        }
    }

    /**
     * Adds a new event with the specified details to the artist's collection.
     *
     * @param name        The name of the event.
     * @param description The description of the event.
     * @param date        The date of the event.
     * @return A status message indicating the success or failure of the operation.
     */
    public String addEvent(final String name, final String description, final Date date) {
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

    /**
     * Adds new merchandise with the specified details to the artist's collection.
     *
     * @param name        The name of the merchandise.
     * @param description The description of the merchandise.
     * @param price       The price of the merchandise.
     * @return A status message indicating the success or failure of the operation.
     */
    public String addMerch(final String name, final String description, final int price) {
        if (hasMerch(name)) {
            return getUsername() + " has merchandise with the same name.";
        }

        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }

        Merch newMerch = new Merch(name, description, price);
        merches.add(newMerch);

        return getUsername() + " has added new merchandise successfully.";

    }

    /**
     * Removes the album with the specified name from the artist's collection.
     *
     * @param name The name of the album to be removed.
     * @return A status message indicating the success or failure of the operation.
     */
    public String removeAlbum(final String name) {
        Album album = getAlbum(name);
        if (hasAlbum(name)) {
            List<User> users = Admin.getUsers();
            for (User user : users) {
                Album sourceAlbum = new Album(",", 0, "", null, "");
                if (user.getPlayer().getSource() != null
                        && user.getPlayer().getSource().getAudioCollection().isAlbum()) {
                    sourceAlbum = (Album) user.getPlayer().getSource().getAudioCollection();
                }
                if (sourceAlbum.getName().equals(name)) {
                    return getUsername() + " can't delete this album.";
                }
                if (user.getSearchBar().getLastSearchType() != null
                        && user.getSearchBar().getLastSearchType().equals("artist")
                        && user.getSearchBar().getLastSelectedUser().
                        getName().equals(getUsername())) {
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

    /**
     * Removes the event with the specified name from the artist's collection.
     *
     * @param name The name of the event to be removed.
     * @return A status message indicating the success or failure of the operation.
     */
    public String removeEvent(final String name) {
        Event event = getEvent(name);
        if (hasEvent(name)) {
            events.remove(event);
            return getUsername() + " deleted the event successfully.";
        } else {
            return getUsername() + " doesn't have an event with the given name.";
        }
    }

    /**
     * Checks if the artist has an event with the specified name.
     *
     * @param name The name of the event.
     * @return True if the artist has an event with the specified name, otherwise false.
     */
    public boolean hasEvent(final String name) {
        for (Event event : getEvents()) {
            if (event.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates whether the provided date is valid or not.
     *
     * @param date The date to be validated.
     * @return True if the date is valid, otherwise false.
     */
    public boolean isValidDate(final Date date) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();

        if (day < MIN_DAY || day > MAX_DAY || month < MIN_MONTH
                || month > MAX_MONTH || year < MIN_YEAR
                || year > MAX_YEAR) {
            return false;
        }

        if (month == FEBRUARY_NR && day > MAX_FEBRUARY_DAY) {
            return false;
        }

        return true;
    }

    /**
     * Parses a date string into a Date object.
     *
     * @param dateString The date string to be parsed.
     * @return The parsed Date object.
     */
    public Date parseDate(final String dateString) {
        String[] dateParts = dateString.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        return new Date(day, month, year);
    }

    /**
     * Checks if the artist has merchandise with the specified name.
     *
     * @param name The name of the merchandise.
     * @return True if the artist has merchandise with the specified name, otherwise false.
     */
    public boolean hasMerch(final String name) {
        for (Merch merch : merches) {
            if (merch.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the artist has an album with the specified name.
     *
     * @param name The name of the album.
     * @return True if the artist has an album with the specified name, otherwise false.
     */
    public boolean hasAlbum(final String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


}
