package app;

import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.pages.Page;
import app.pages.PageFactory;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();
        String message;
        ArrayList<String> results;

        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
            results = new ArrayList<>();
        } else {
            results = user.search(filters, type);
            message = "Search returned " + results.size() + " results";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;

    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            message = user.select(commandInput.getItemNumber());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            message = user.load();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            message = user.playPause();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            message = user.repeat();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message;
        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            message = user.shuffle(seed);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            message = user.like();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.createPlaylist(commandInput.getPlaylistName(),
                commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Switches the connection status (online/offline)
     * for the user specified in the provided command input.
     *
     * @param commandInput The input containing the command details,
     * including the username.
     * @return An ObjectNode containing information about the command execution,
     * including the user's connection status switch message.
     *
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (user != null) {
            String message = user.switchConnectionStatus();
            objectNode.put("message", message);
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Retrieves a list of online users and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details.
     * @return An ObjectNode containing information about the command execution,
     * including the list of online users.
     *
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> onlineUsers = Admin.getOnlineUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result",  objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    /**
     * Adds a new user to the system based on the provided command input and
     * returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details, including
     * the username, age, city, and user type.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        int age = commandInput.getAge();
        String city = commandInput.getCity();
        String type = commandInput.getType();
        String message = Admin.addUser(username, age, city, type);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new album to the system based on the provided command
     * input and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username, album name, release year, description, and songs.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                String name = commandInput.getName();
                int releaseYear = commandInput.getReleaseYear();
                String description = commandInput.getDescription();
                List<Song> songs = commandInput.getSongs();
                String owner = commandInput.getUsername();

                String message = artist.addAlbum(name, releaseYear, description, songs, owner);
                objectNode.put("message", message);
            } else {
                 objectNode.put("message",  commandInput.getUsername() + " is not an artist.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Retrieves and displays the albums of a specific artist based
     * on the provided command input.
     *
     * @param commandInput The input containing the command details,
     * including the artist's username.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        String artistName = commandInput.getUsername();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        List<ObjectNode> result = Admin.showAlbums(artistName);
        objectNode.set("result", objectMapper.valueToTree(result));

        return  objectNode;
    }

    /**
     * Retrieves and prints the current page content for the specified user
     * based on the provided command input.
     *
     * @param commandInput The input containing the command details,
     * including the username.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user.isOnline()) {
            Page page = PageFactory.createPage(user);
            message = page.printCurrentPage();
        } else {
            message = user.getUsername() + " is offline.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new event to the system based on the provided command input
     * and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username, event name, description, and date.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                Date date = artist.parseDate(commandInput.getDate());
                String message = artist.addEvent(commandInput.getName(),
                        commandInput.getDescription(), date);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not an artist.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Adds a new merchandise item to the system based on the provided
     * command input and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username, merchandise name, description, and price.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                String message = artist.addMerch(commandInput.getName(),
                        commandInput.getDescription(), commandInput.getPrice());
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not an artist.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Retrieves a list of all users in the system and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<String> users = Admin.getAllUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(users));

        return objectNode;
    }

    /**
     * Deletes a user from the system based on the provided command input
     * and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username to be deleted.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static  ObjectNode deleteUser(final CommandInput commandInput) {
        String message = Admin.deleteUser(commandInput.getUsername());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new podcast to the system based on the provided command
     * input and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username, podcast name, and episodes.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("host")) {
                Host artist = (Host) user;
                String name = commandInput.getName();
                List<Episode> episode = Admin.convertEpisodes(commandInput.getEpisodes());
                String owner = commandInput.getUsername();

                String message = artist.addPodcast(name, owner, episode);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not a host.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Adds a new announcement to the system based on the provided command input
     * and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details, including
     * the username, announcement name, and description.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("host")) {
                Host host = (Host) user;
                String name = commandInput.getName();
                String description = commandInput.getDescription();

                String message = host.addAnouncement(name, description);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not a host.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Removes an announcement from the system based on the provided command
     * input and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username and announcement name to be removed.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("host")) {
                Host host = (Host) user;
                String name = commandInput.getName();

                String message = host.removeAnnouncement(name);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not a host.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Retrieves and displays the podcasts of a specific host based on the
     * provided command input.
     *
     * @param commandInput The input containing the command details, including
     * the host's username.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        String hostName = commandInput.getUsername();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        List<ObjectNode> result = Admin.showPodcasts(hostName);
        objectNode.set("result", objectMapper.valueToTree(result));

        return  objectNode;
    }

    /**
     * Removes an album from the system based on the provided command input
     * and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username and album name to be removed.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                String name = commandInput.getName();

                String message = artist.removeAlbum(name);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not an artist.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Changes the current page for the specified user based on the provided command input.
     *
     * @param commandInput The input containing the command details,
     * including the username and the next page to be set.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String message = user.changePage(commandInput.getNextPage());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Removes a podcast from the system based on the provided command input
     * and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username and podcast name to be removed.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("host")) {
                Host host = (Host) user;
                String name = commandInput.getName();

                String message = host.removePodcast(name);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not a host.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;

    }

    /**
     * Removes an event from the system based on the provided command input
     * and returns the result in an ObjectNode format.
     *
     * @param commandInput The input containing the command details,
     * including the username and event name to be removed.
     * @return An ObjectNode containing information about the command execution,
     * including the result message.
     *
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            if (user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                String name = commandInput.getName();

                String message = artist.removeEvent(name);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not a artist.");
            }
        } else {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    /**
     * Retrieves information about the top 5 albums and encapsulates it in a JSON ObjectNode.
     *
     * @param commandInput The input containing command-related information.
     * @return An ObjectNode representing the result with details about the top 5 albums.
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {

        List<String> topAlbums = Admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(topAlbums));

        return objectNode;

    }

    /**
     * Retrieves information about the top 5 artists and encapsulates it in a JSON ObjectNode.
     *
     * @param commandInput The input containing command-related information.
     * @return An ObjectNode representing the result with details about the top 5 artists.
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {

        List<String> topArtists = Admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(topArtists));

        return objectNode;

    }

}
