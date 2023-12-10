package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LikedContentPage implements Page {
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;

    public LikedContentPage(ArrayList<Song> likedSongs, ArrayList<Playlist> followedPlaylists){
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }

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

        return "Liked songs:\n\t[" + String.join(", ", likedSongsDetails) +
                "]\n\nFollowed playlists:\n\t[" + String.join(", ", followedPlaylistsDetails) + "]";
    }

//    private void sortLikedSongs() {
//        likedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
//        likedSongs = new ArrayList<>(likedSongs.subList(0, Math.min(likedSongs.size(), 5)));
//    }
//
//    private void sortFollowedPlaylists() {
//        followedPlaylists.sort(Comparator.comparingInt(this::getTotalLikesInPlaylist).reversed());
//        followedPlaylists = new ArrayList<>(followedPlaylists.subList(0, Math.min(followedPlaylists.size(), 5)));
//    }
//
//    private int getTotalLikesInPlaylist(Playlist playlist) {
//        return playlist.getSongs().stream().mapToInt(Song::getLikes).sum();
//    }

}
