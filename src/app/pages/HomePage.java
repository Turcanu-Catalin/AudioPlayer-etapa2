package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class HomePage implements Page {
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;

    private static final int LIMIT = 5;

    public HomePage(final ArrayList<Song> likedSongs, final ArrayList<Playlist> followedPlaylists) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }

    /**
     * Retrieves a formatted string representation of the user's
     * liked songs and followed playlists,
     * displaying the names of the songs and playlists along with relevant details.
     *
     * @return A formatted string containing
     * details of liked songs and followed playlists.
     */
    @Override
    public String printCurrentPage() {
        sortLikedSongs();
        sortFollowedPlaylists();

        List<String> likedSongsName = new ArrayList<>();
        for (Song song : likedSongs) {
            likedSongsName.add(song.getName());
        }

        List<String> followedPlaylistsName = new ArrayList<>();
        for (Playlist playlist : followedPlaylists) {
            followedPlaylistsName.add(playlist.getName());
        }

        return "Liked songs:\n\t" + likedSongsName
                + "\n\nFollowed playlists:\n\t" + followedPlaylistsName;
    }

    /**
     * Sorts the liked songs based on the number of likes in
     * descending order and limits the result to a specified number.
     */
    private void sortLikedSongs() {
        likedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        likedSongs = new ArrayList<>(likedSongs.subList(0, Math.min(likedSongs.size(), LIMIT)));
    }

    /**
     * Sorts the followed playlists based on the total number
     * of likes of songs in the playlist in descending order
     * and limits the result to a specified number.
     */
    private void sortFollowedPlaylists() {
        followedPlaylists.sort(Comparator.comparingInt(this::getTotalLikesInPlaylist).reversed());
        followedPlaylists =
                new ArrayList<>(followedPlaylists.subList(0,
                        Math.min(followedPlaylists.size(), LIMIT)));
    }

    /**
     * Calculates the total number of likes for all songs in the given playlist.
     *
     * @param playlist The playlist for which to calculate the total likes.
     * @return The total number of likes for all songs in the playlist.
     */
    private int getTotalLikesInPlaylist(final Playlist playlist) {
        return playlist.getSongs().stream().mapToInt(Song::getLikes).sum();
    }

}
