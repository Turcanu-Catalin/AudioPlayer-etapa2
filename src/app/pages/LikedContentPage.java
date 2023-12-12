package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class LikedContentPage implements Page {
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;

    public LikedContentPage(final ArrayList<Song> likedSongs,
                            final ArrayList<Playlist> followedPlaylists) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }

    /**
     * Retrieves a formatted string representation of the
     * user's liked songs and followed playlists,
     * displaying the names and artists of the liked songs,
     * as well as the names and owners of the followed playlists.
     *
     * @return A formatted string containing details of liked songs and followed playlists.
     */
    @Override
    public String printCurrentPage() {

        List<String> likedSongsDetails = new ArrayList<>();
        for (Song song : likedSongs) {
            likedSongsDetails.add(song.getName() + " - " + song.getArtist());
        }

        List<String> followedPlaylistsDetails = new ArrayList<>();
        for (Playlist playlist : followedPlaylists) {
            followedPlaylistsDetails.add(playlist.getName() + " - " + playlist.getOwner());
        }

        return "Liked songs:\n\t[" + String.join(", ", likedSongsDetails)
                + "]\n\nFollowed playlists:\n\t["
                + String.join(", ", followedPlaylistsDetails) + "]";
    }
}
