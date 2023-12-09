package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.player.PlayerSource;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.ArtistsEntry;
import app.utils.Event;
import app.utils.HostEntry;
import app.utils.Merch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.*;
import lombok.Getter;
import net.sf.saxon.expr.Component;

import javax.xml.transform.Source;
import java.util.*;

public class Admin {
    private static Admin instance;
    @Getter
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;

    private Admin(List<User> users, List<Song> songs,List<Podcast> podcasts) {
        this.users = users;
        this.songs = songs;
        this.podcasts = podcasts;
        this.timestamp = 0;
    }

    public static Admin getInstance(List<User> users, List<Song> songs,List<Podcast> podcasts) {
        if (instance == null) {
            instance = new Admin(users,songs,podcasts);
        }
        return instance;
    }

    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static List<ArtistsEntry> getArtistsEntries() {
        List<ArtistsEntry> artistEntries = new ArrayList<>();
        List<Artist> artists = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("artist")) {
                artists.add((Artist) user);
            }
        }

        for (Artist artist : artists) {
            ArtistsEntry artistaEntry = new ArtistsEntry(artist.getUsername(),artist.getAge(),artist.getCity(),artist.getAlbums(),artist.getEvents(),artist.getMerches());
            artistEntries.add(artistaEntry);
        }

        return  artistEntries;
    }

    public static List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();

        for (User user : users) {
            if(user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                List<Album> artistAlbums = artist.getAlbums();
                albums.addAll(artistAlbums);
            }
        }

        return albums;
    }

    public static List<Event> getEvents() {
        List<Event> events = new ArrayList<>();

        for (User user : users) {
            if(user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                List<Event> artistEvents = artist.getEvents();
                events.addAll(artistEvents);
            }
        }

        return events;
    }

    public static List<Merch> getMerch() {
        List<Merch> merch = new ArrayList<>();

        for (User user : users) {
            if(user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                List<Merch> artistMerch = artist.getMerches();
                merch.addAll(artistMerch);
            }
        }

        return merch;
    }

    public static List<Playlist> getAllPlaylists() {
        List<Playlist> playists = new ArrayList<>();

        for (User user : users) {
            if(user.getUserType().equals("normalUser")) {
                List<Playlist> userPlaylists = user.getPlaylists();
                playists.addAll(userPlaylists);
            }
        }

        return playists;
    }

    public static void updateTimestamp(int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if (user.getUserType().equals("normalUser")) {
                user.simulateTime(elapsed);
            }
        }
    }

    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : users){
            if(user.isOnline() == true){
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    public static String addUser(String username, int age, String city, String type) {
        User user = getUser(username);
        if (user != null && users.contains(user)) {
            return "The username " + user.getUsername() + " is already taken.";
        } else {
            User newUser;
            if (type.equals("artist")) {
                newUser = new Artist(username, age, city);
                newUser.setOnline(false);
            } else if (type.equals("host")){
                newUser = new Host(username, age, city);
                newUser.setOnline(false);
            } else {
                newUser = new User(username, age, city);
            }

            users.add(newUser);
            return "The username " + newUser.getUsername() + " has been added successfully.";
        }
    }


    public static List<ObjectNode> showAlbums(String artistUsername) {
        User user = getUser(artistUsername);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> albumResults = new ArrayList<>();

        if (user.getUserType().equals("artist")) {
            Artist artist = (Artist) user;
            List<Album> artistAlbums = artist.getAlbums();

            for (Album album : artistAlbums) {
                ObjectNode albumNode = objectMapper.createObjectNode();
                albumNode.put("name", album.getName());

                ArrayNode songNamesArray = objectMapper.createArrayNode();
                for (Song song : album.getSongs()) {
                    songNamesArray.add(song.getName());
                }
                albumNode.set("songs", songNamesArray);

                albumResults.add(albumNode);
            }
        } else {
            ObjectNode messageNode = objectMapper.createObjectNode();
            messageNode.put("message", "The user " + artistUsername + " is not an artist.");
            albumResults.add(messageNode);
        }

        return albumResults;
    }

    public static List<String> getAllUsers() {
        List<String> normalUsers = new ArrayList<>();
        List<String> artists = new ArrayList<>();
        List<String> hosts = new ArrayList<>();

        for (User user : users) {
            String username = user.getUsername();
            if (user.getUserType().equals("artist")) {
                artists.add(username);
//            } else if (user instanceof Artist) {
//                hosts.add(username);
//            }
        } else {
                normalUsers.add(username);
            }
        }

        // Concatenate the lists in the specified order
        List<String> result = new ArrayList<>();
        result.addAll(normalUsers);
        result.addAll(artists);
//        result.addAll(hosts);

        return result;
    }

    public static String deleteUser(String username) {
        User deleteUser = getUser(username);
        if (deleteUser != null) {
            if (deleteUser.getUserType().equals("normalUser")) {

                List<Playlist> userPlaylists = deleteUser.getPlaylists();
                List<Playlist> allPlaylists = getAllPlaylists();
                allPlaylists.removeAll(userPlaylists);

                for(User user : users){
                    for(Playlist playlist : userPlaylists){
                        user.deleteFollowedPlaylist(playlist);
                    }
                }

                for (Song song : deleteUser.getLikedSongs()) {
                    song.dislike();
                }

                for (Playlist playlist : deleteUser.getFollowedPlaylists()) {
                    playlist.decreaseFollowers();
                }

                users.remove(deleteUser);

                return username + " was successfully deleted.";
            }

            if (deleteUser.getUserType().equals("artist")) {
                Artist artist = (Artist) deleteUser;


                for(User user : users) {
                    Song sourceSong = new Song();
                    if (user.getPlayer().getSource() != null) {
                        sourceSong = (Song) user.getPlayer().getSource().getAudioFile();
                    }
                    for (Album album : getAlbums()) {
                        for (Song song : album.getSongs()) {
                            if (song.getName().equals(sourceSong.getName())) {
                                return username + " can't be deleted.";
                            }
                        }
                    }
                }

                for(User user : users) {
                    Album sourceAlbum = new Album(",", 0, "", null, "");
                    if (user.getPlayer().getSource() != null) {
                        sourceAlbum = (Album) user.getPlayer().getSource().getAudioCollection();
                    }
                    for (Album album : getAlbums()) {
                        if (album.getName().equals(sourceAlbum.getName())) {
                            return username + " can't be deleted.";
                        }
                    }
                }

                List<Album> artistAlbums = artist.getAlbums();
                for (User user : users){
                    if(user.getUserType().equals("normalUser")){
                        for (Album album : artistAlbums){
                            for(Song song : album.getSongs()){
                                user.deleteLikedSong(song);
                            }
                        }
                    }
                }

                for(Album album : artistAlbums) {
                    deleteAlbumSongs(album);
                }
                users.remove(deleteUser);

                return username + " was successfully deleted.";
            }
        }

        return "The username " + username + " doesn't exist.";
    }

    public static List<ObjectNode> showPodcasts(String hostUsername) {
        User user = getUser(hostUsername);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ObjectNode> podcastResults = new ArrayList<>();

        if (user.getUserType().equals("host")) {
            Host host = (Host) user;
            List<Podcast> hostPodcasts = host.getPodcasts();

            for (Podcast podcast : hostPodcasts) {
                ObjectNode podcastNode = objectMapper.createObjectNode();
                podcastNode.put("name", podcast.getName());

                ArrayNode episodesNamesArray = objectMapper.createArrayNode();
                for (Episode episode : podcast.getEpisodes()) {
                    episodesNamesArray.add(episode.getName());
                }
                podcastNode.set("episodes", episodesNamesArray);

                podcastResults.add(podcastNode);
            }
        } else {
            ObjectNode messageNode = objectMapper.createObjectNode();
            messageNode.put("message", "The user " + hostUsername + " is not a host.");
            podcastResults.add(messageNode);
        }

        return podcastResults;
    }

    public static List<HostEntry> getHostEntries() {
        List<HostEntry> hostEntries = new ArrayList<>();
        List<Host> hosts = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("host")) {
                hosts.add((Host) user);
            }
        }

        for (Host host : hosts) {
            HostEntry hostEntry = new HostEntry(host.getUsername(),host.getAge(),host.getCity(),host.getPodcasts(),host.getAnnouncements());
            hostEntries.add(hostEntry);
        }

        return hostEntries;
    }


    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }

    public static void addAlbumSongs(Album album) {
        songs.addAll(album.getSongs());
    }

    public static void addPodcast(Podcast podcast){
        podcasts.add(podcast);
    }

    public static void deleteAlbumSongs(Album album) {
        for(Song song : album.getSongs()){
            songs.remove(song);
        }
    }

    public static void deletePodcast(Podcast podcast){
        podcasts.remove(podcast);
    }

    public static List<Episode> convertEpisodes(List <EpisodeInput> episodesInputs){
        List<Episode> episodes = new ArrayList<>();
        for(EpisodeInput episodeInput : episodesInputs){
            Episode episode = new Episode(episodeInput.getName(),episodeInput.getDuration(),episodeInput.getDescription());
            episodes.add(episode);
        }
        return episodes;
    }
}
