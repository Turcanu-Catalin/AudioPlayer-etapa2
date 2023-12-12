package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.ArtistsEntry;
import app.utils.HostEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public final class Admin {
    private static Admin instance;
    @Getter
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private Admin(final List<User> users, final List<Song> songs, final List<Podcast> podcasts) {
        this.users = users;
        this.songs = songs;
        this.podcasts = podcasts;
        this.timestamp = 0;
    }

    /**
     * Returns the singleton instance of the Admin class.
     * If the instance does not exist, it creates a new one with the provided lists of users,
     * songs, and podcasts. Otherwise, it returns the existing instance.
     *
     * @param allUsers     the list of all users
     * @param allSongs     the list of all songs
     * @param allPodcasts  the list of all podcasts
     * @return the singleton instance of the {@code Admin} class
     */
    public static Admin getInstance(final List<User> allUsers, final List<Song> allSongs,
                                    final List<Podcast> allPodcasts) {
        if (instance == null) {
            instance = new Admin(allUsers, allSongs, allPodcasts);
        }
        return instance;
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                        episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of ArtistsEntry objects representing information about artists.
     *
     * @return a list of code ArtistsEntry objects representing information about artists
     */
    public static List<ArtistsEntry> getArtistsEntries() {
        List<ArtistsEntry> artistEntries = new ArrayList<>();
        List<Artist> artists = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("artist")) {
                artists.add((Artist) user);
            }
        }

        for (Artist artist : artists) {
            ArtistsEntry artistaEntry =
                    new ArtistsEntry(artist.getUsername(), artist.getAge(),
                            artist.getCity(), artist.getAlbums(),
                            artist.getEvents(), artist.getMerches());
            artistEntries.add(artistaEntry);
        }

        return  artistEntries;
    }

    /**
     * Retrieves a list of Album objects representing all albums created by artists.
     *
     * @return a list of Album objects representing all albums created by artists
     */
    public static List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();

        for (User user : users) {
            if (user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                List<Album> artistAlbums = artist.getAlbums();
                albums.addAll(artistAlbums);
            }
        }

        return albums;
    }

    /**
     * Retrieves a list of all playlists created by users in the system.
     *
     * @return a list of all playlists
     */
    public static List<Playlist> getAllPlaylists() {
        List<Playlist> playists = new ArrayList<>();

        for (User user : users) {
            if (user.getUserType().equals("user")) {
                List<Playlist> userPlaylists = user.getPlaylists();
                playists.addAll(userPlaylists);
            }
        }

        return playists;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Retrieves a list of usernames for all users who are currently online.
     *
     * @return a list of online user usernames
     */
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isOnline()) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * Adds a new user to the system based on the provided parameters.
     *
     * @param username the username for the new user
     * @param age the age of the new user
     * @param city the city of residence of the new user
     * @param type the type of user to be created ("user", "artist", or "host")
     * @return a message indicating the result of the operation (success or failure)
     */
    public static String addUser(final String username, final int age,
                                  final String city, final String type) {
        User user = getUser(username);
        if (user != null && users.contains(user)) {
            return "The username " + user.getUsername() + " is already taken.";
        } else {
            User newUser;
            if (type.equals("artist")) {
                newUser = new Artist(username, age, city);
                newUser.setOnline(false);
            } else if (type.equals("host")) {
                newUser = new Host(username, age, city);
                newUser.setOnline(false);
            } else {
                newUser = new User(username, age, city);
            }

            users.add(newUser);
            return "The username " + newUser.getUsername() + " has been added successfully.";
        }
    }

    /**
     * Retrieves a list of album information for a given artist's albums, including album names
     * and associated song names.
     *
     * @param artistUsername the username of the artist
     * @return a list of ObjectNode containing album information or an error message node
     */
    public static List<ObjectNode> showAlbums(final String artistUsername) {
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

    /**
     * Retrieves a list of all usernames in the system categorized
     * by user type (normal users, artists, hosts).
     *
     * @return a list of usernames, grouped by user type
     */
    public static List<String> getAllUsers() {
        List<String> normalUsers = new ArrayList<>();
        List<String> artists = new ArrayList<>();
        List<String> hosts = new ArrayList<>();

        for (User user : users) {
            String username = user.getUsername();
            if (user.getUserType().equals("artist")) {
                artists.add(username);
            } else if (user.getUserType().equals("host")) {
                hosts.add(username);
        } else {
                normalUsers.add(username);
            }
        }

        List<String> result = new ArrayList<>();
        result.addAll(normalUsers);
        result.addAll(artists);
        result.addAll(hosts);

        return result;
    }

    /**
     * Deletes a user from the system based on the provided username.
     *
     * @param username the username of the user to be deleted
     * @return a message indicating the result of the deletion operation
     */
    public static String deleteUser(final String username) {
        User deleteUser = getUser(username);
        if (deleteUser != null) {
            if (deleteUser.getUserType().equals("user")) {
                return deleteUserAsUser(deleteUser);
            } else if (deleteUser.getUserType().equals("artist")) {
                return deleteUserAsArtist((Artist) deleteUser);
            } else if (deleteUser.getUserType().equals("host")) {
                return deleteUserAsHost((Host) deleteUser);
            }
        }
        return "The username " + username + " doesn't exist.";
    }

    /**
     * Deletes a user along with associated playlists, liked songs, and followed playlists.
     *
     * @param user The user to be deleted.
     * @return A message indicating the success or failure of the deletion process for the user.
     */
    private static String deleteUserAsUser(final User user) {
        List<Playlist> userPlaylists = user.getPlaylists();

        for (User u : users) {
            Playlist sourcePlaylist = new Playlist("", "");
            if (u.getPlayer().getSource() != null
                    && u.getPlayer().getSource().getAudioCollection() != null
                    && u.getPlayer().getSource().getAudioCollection().isPlaylist()) {
                sourcePlaylist = (Playlist) u.getPlayer().getSource().getAudioCollection();
            }
            for (Playlist playlist : userPlaylists) {
                if (sourcePlaylist != null && playlist.getName().equals(sourcePlaylist.getName())) {
                    return user.getUsername() + " can't be deleted.";
                }
            }
        }

        List<Playlist> allPlaylists = getAllPlaylists();
        allPlaylists.removeAll(userPlaylists);

        for (User u : users) {
            for (Playlist playlist : userPlaylists) {
                u.deleteFollowedPlaylist(playlist);
            }
        }

        for (Song song : user.getLikedSongs()) {
            song.dislike();
        }

        for (Playlist playlist : user.getFollowedPlaylists()) {
            playlist.decreaseFollowers();
        }

        users.remove(user);

        return user.getUsername() + " was successfully deleted.";
    }

    /**
     * Deletes an artist along with associated albums and songs from user liked lists.
     *
     * @param artist The artist to be deleted.
     * @return A message indicating the success or failure of the deletion process for the artist.
     */
    private static String deleteUserAsArtist(final Artist artist) {
        List<Album> artistAlbums = artist.getAlbums();

        for (User user : users) {
            Song sourceSong = new Song();
            if (user.getPlayer().getSource() != null
                    && user.getPlayer().getSource().getAudioFile().isSong()) {
                sourceSong = (Song) user.getPlayer().getSource().getAudioFile();
            }
            for (Album album : artistAlbums) {
                for (Song song : album.getSongs()) {
                    if (song.getName().equals(sourceSong.getName())) {
                        return artist.getUsername() + " can't be deleted.";
                    }
                }
            }
        }

        for (User user : users) {
            Album sourceAlbum = new Album(",", 0, "", null, "");
            if (user.getPlayer().getSource() != null
                    && user.getPlayer().getSource().getAudioCollection().isAlbum()) {
                sourceAlbum = (Album) user.getPlayer().getSource().getAudioCollection();
            }
            for (Album album : artistAlbums) {
                if (album.getName().equals(sourceAlbum.getName())) {
                    return artist.getUsername() + " can't be deleted.";
                }
            }
        }

        for (User user : users) {
            if (user.getCurrentPage().equals(artist.getUsername())) {
                return artist.getUsername() + " can't be deleted.";
            }
        }

        for (User user : users) {
            if (user.getUserType().equals("user")) {
                for (Album album : artistAlbums) {
                    for (Song song : album.getSongs()) {
                        user.deleteLikedSong(song);
                    }
                }
            }
        }

        for (Album album : artistAlbums) {
            deleteAlbumSongs(album);
        }

        users.remove(artist);

        return artist.getUsername() + " was successfully deleted.";
    }

    /**
     * Deletes a host along with associated podcasts.
     *
     * @param host The host to be deleted.
     * @return A message indicating the success or failure of the deletion process for the host.
     */
    private static String deleteUserAsHost(final Host host) {
        for (User user : users) {
            Podcast sourcePodcast = new Podcast("", "", null);
            if (user.getPlayer().getSource() != null
                    && user.getPlayer().getSource().getAudioCollection().isPodcast()) {
                sourcePodcast = (Podcast) user.getPlayer().getSource().getAudioCollection();
            }
            for (Podcast podcast : host.getPodcasts()) {
                if (podcast.getName().equals(sourcePodcast.getName())) {
                    return host.getUsername() + " can't be deleted.";
                }
            }
        }

        for (User user : users) {
            if (user.getCurrentPage().equals(host.getUsername())) {
                return host.getUsername() + " can't be deleted.";
            }
        }

        for (Podcast podcast : host.getPodcasts()) {
            deletePodcast(podcast);
        }

        users.remove(host);

        return host.getUsername() + " was successfully deleted.";
    }


    /**
     * Retrieves a list of podcast information for a given host's podcasts, including podcast names
     * and associated episode names.
     *
     * @param hostUsername the username of the host
     * @return a list of ObjectNode containing podcast information or an error message node
     */
    public static List<ObjectNode> showPodcasts(final String hostUsername) {
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

    /**
     * Retrieves a list of HostEntry objects containing information about all hosts,
     * including username, age, city, podcasts, and announcements.
     *
     * @return a list of HostEntry objects
     */
    public static List<HostEntry> getHostEntries() {
        List<HostEntry> hostEntries = new ArrayList<>();
        List<Host> hosts = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("host")) {
                hosts.add((Host) user);
            }
        }

        for (Host host : hosts) {
            HostEntry hostEntry =
                    new HostEntry(host.getUsername(), host.getAge(), host.getCity(),
                            host.getPodcasts(), host.getAnnouncements());
            hostEntries.add(hostEntry);
        }

        return hostEntries;
    }


    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * Adds all songs from the given album to the list of songs.
     *
     * @param album the album containing songs to be added
     */
    public static void addAlbumSongs(final Album album) {
        songs.addAll(album.getSongs());
    }

    /**
     * Adds the given podcast to the list of podcasts.
     *
     * @param podcast the podcast to be added
     */
    public static void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Deletes all songs from the given album from the list of songs.
     *
     * @param album the album containing songs to be deleted
     */
    public static void deleteAlbumSongs(final Album album) {
        for (Song song : album.getSongs()) {
            songs.remove(song);
        }
    }

    /**
     * Deletes the given podcast from the list of podcasts.
     *
     * @param podcast the podcast to be deleted
     */
    public static void deletePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * Converts a list of episode inputs to a list of Episode objects.
     *
     * @param episodesInputs the list of EpisodeInput objects
     * @return the list of Episode objects
     */
    public static List<Episode> convertEpisodes(final List<EpisodeInput> episodesInputs) {
        List<Episode> episodes = new ArrayList<>();
        for (EpisodeInput episodeInput : episodesInputs) {
            Episode episode = new Episode(
                    episodeInput.getName(),
                    episodeInput.getDuration(),
                    episodeInput.getDescription()
            );
            episodes.add(episode);
        }
        return episodes;
    }


    /**
     * Gets the top 5 albums based on total likes.
     *
     * @return the list of top 5 album names
     */
    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());

        sortedAlbums.sort(Comparator.comparingInt(Admin::getTotalLikesInAlbum)
                .reversed()
                .thenComparing(Album::getName));

        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= LIMIT) {
                break;
            }
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }

    /**
     * Gets the top 5 artists based on total likes.
     *
     * @return the list of top 5 artist usernames
     */
    public static List<String> getTop5Artists() {
        List<Artist> sortedArtists = new ArrayList<>(getArtists());
        sortedArtists.sort(Comparator.comparingInt(Admin::getTotalArtistLikes)
                .reversed()
                .thenComparing(Artist::getUsername, Comparator.naturalOrder()));
        List<String> topArtists = new ArrayList<>();
        int count = 0;
        for (Artist artist : sortedArtists) {
            if (count >= LIMIT) {
                break;
            }
            topArtists.add(artist.getUsername());
            count++;
        }
        return topArtists;
    }


    /**
     * Gets the total number of likes in an album.
     *
     * @param album the album to calculate likes for
     * @return the total number of likes in the album
     */
    public static int getTotalLikesInAlbum(final Album album) {
        return album.getSongs().stream().mapToInt(Song::getLikes).sum();
    }

    /**
     * Gets the total number of likes for an artist based on all their albums.
     *
     * @param artist the artist to calculate likes for
     * @return the total number of likes for the artist
     */
    public static int getTotalArtistLikes(final Artist artist) {
        return artist.getAlbums().stream().mapToInt(Admin::getTotalLikesInAlbum).sum();
    }

    /**
     * Gets a list of all artists in the system.
     *
     * @return the list of artists
     */
    public static List<Artist> getArtists() {
        ArrayList<Artist> artists = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals("artist")) {
                artists.add((Artist) user);
            }
        }
        return artists;
    }

}
