package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class HomePage implements Page{
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;

    public HomePage(ArrayList<Song> likedSongs, ArrayList<Playlist> followedPlaylists){
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
    }

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

        return "Liked songs:\n\t" + likedSongsName + "\n\nFollowed playlists:\n\t" + followedPlaylistsName;
    }

    private void sortLikedSongs() {
        likedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        likedSongs = new ArrayList<>(likedSongs.subList(0, Math.min(likedSongs.size(), 5)));
    }

    private void sortFollowedPlaylists() {
        followedPlaylists.sort(Comparator.comparingInt(this::getTotalLikesInPlaylist).reversed());
        followedPlaylists = new ArrayList<>(followedPlaylists.subList(0, Math.min(followedPlaylists.size(), 5)));
    }

    private int getTotalLikesInPlaylist(Playlist playlist) {
        return playlist.getSongs().stream().mapToInt(Song::getLikes).sum();
    }

}
