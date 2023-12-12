
package app.audio.Collections;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;

import java.util.List;

public class Album extends AudioCollection {
    private String albumName;
    private int releaseYear;
    private String description;
    private List<Song> songs;
    private String albumOwner;

    /**
     * Constructs an Album with the specified details.
     *
     * @param albumName   The name of the album.
     * @param releaseYear The release year of the album.
     * @param description A brief description of the album.
     * @param songs       The list of songs in the album.
     * @param albumOwner  The owner of the album.
     */
    public Album(final String albumName, final int releaseYear, final String description,
                 final List<Song> songs, final String albumOwner) {
        super(albumName, albumOwner);
        this.albumName = albumName;
        this.albumOwner = albumOwner;
        this.releaseYear = releaseYear;
        this.songs = songs;
        this.description = description;
    }

    /**
     * Sets the name of the album.
     *
     * @param name The new name for the album.
     */
    public void setName(final String name) {
        this.albumName = name;
    }

    /**
     * Gets the release year of the album.
     *
     * @return The release year.
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets the release year of the album.
     *
     * @param releaseYear The new release year.
     */
    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Gets the description of the album.
     *
     * @return The description of the album.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the album.
     *
     * @param description The new description for the album.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets the list of songs in the album.
     *
     * @return The list of songs.
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * Sets the list of songs in the album.
     *
     * @param songs The new list of songs for the album.
     */
    public void setSongs(final List<Song> songs) {
        this.songs = songs;
    }

    /**
     * Gets the owner of the album.
     *
     * @return The owner of the album.
     */
    public String getAlbumOwner() {
        return albumOwner;
    }

    /**
     * Sets the owner of the album.
     *
     * @param albumOwner The new owner for the album.
     */
    public void setAlbumOwner(final String albumOwner) {
        this.albumOwner = albumOwner;
    }

    /**
     * Gets the number of tracks in the album.
     *
     * @return The number of tracks.
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * Gets the audio file (song) at the specified index in the album.
     *
     * @param index The index of the song.
     * @return The audio file (song) at the specified index.
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    /**
     * Checks if the album's owner matches the provided user.
     *
     * @param user The user to compare.
     * @return True if the owners match, false otherwise.
     */
    @Override
    public boolean matchesOwner(final String user) {
        return this.getAlbumOwner().equals(user);
    }

    /**
     * Indicates whether the current object represents an album.
     *
     * @return True, since this class represents an album.
     */
    @Override
    public boolean isAlbum() {
        return true;
    }

}
