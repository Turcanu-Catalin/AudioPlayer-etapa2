package app.audio.Files;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Song.
 */
@Getter
public final class Song extends AudioFile {
    private final String album;
    private final ArrayList<String> tags;
    private final String lyrics;
    private final String genre;
    private final Integer releaseYear;
    private final String artist;
    private Integer likes;

    public Song() {
        super("", 0);
        this.album = "";
        this.tags = new ArrayList<>();
        this.lyrics = "";
        this.genre = "";
        this.releaseYear = 0;
        this.artist = "";
        this.likes = 0;
    }

    /**
     * Instantiates a new Song.
     *
     * @param name        the name
     * @param duration    the duration
     * @param album       the album
     * @param tags        the tags
     * @param lyrics      the lyrics
     * @param genre       the genre
     * @param releaseYear the release year
     * @param artist      the artist
     */
    public Song(final String name, final Integer duration, final String album,
                final ArrayList<String> tags, final String lyrics,
                final String genre, final Integer releaseYear, final String artist) {
        super(name, duration);
        this.album = album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;
        this.likes = 0;
    }

    @Override
    public boolean matchesAlbum(final String songAlbum) {
        return this.getAlbum().equalsIgnoreCase(songAlbum);
    }

    @Override
    public boolean matchesTags(final ArrayList<String> entrySongTags) {
        List<String> songTags = new ArrayList<>();
        for (String tag : this.getTags()) {
            songTags.add(tag.toLowerCase());
        }

        for (String tag : entrySongTags) {
            if (!songTags.contains(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean matchesLyrics(final String songLyrics) {
        return this.getLyrics().toLowerCase().contains(songLyrics.toLowerCase());
    }

    @Override
    public boolean matchesGenre(final String songGenre) {
        return this.getGenre().equalsIgnoreCase(songGenre);
    }

    @Override
    public boolean matchesArtist(final String songArtist) {
        return this.getArtist().equalsIgnoreCase(songArtist);
    }

    @Override
    public boolean matchesReleaseYear(final String songReleaseYear) {
        return filterByYear(this.getReleaseYear(), songReleaseYear);
    }

    private static boolean filterByYear(final int year, final String query) {
        if (query.startsWith("<")) {
            return year < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return year > Integer.parseInt(query.substring(1));
        } else {
            return year == Integer.parseInt(query);
        }
    }

    /**
     * Like.
     */
    public void like() {
        likes++;
    }

    /**
     * Dislike.
     */
    public void dislike() {
        likes--;
    }
    @Override
    public boolean isSong() {
        return true;
    }
}
