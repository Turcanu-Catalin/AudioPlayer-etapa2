package app;

import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pages.*;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.ArtistsEntry;
import app.utils.Date;
import app.utils.HostEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

public class CommandRunner {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectNode search(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();
        String message;
        ArrayList<String> results;

        if (user.isOnline() == false) {
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

    public static ObjectNode select(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
//        if(user.isOnline() == false) {
//            message = commandInput.getUsername() + " is offline.";
//        } else {
            message = user.select(commandInput.getItemNumber());
//        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode load(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
//        if(user.isOnline() == false) {
//            message = commandInput.getUsername() + " is offline.";
//        } else {
            message = user.load();
//        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode playPause(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
//        if(user.isOnline() == false) {
//            message = commandInput.getUsername() + " is offline.";
//        } else {
            message = user.playPause();
//        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode repeat(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
//        if(user.isOnline() == false) {
//            message = commandInput.getUsername() + " is offline.";
//        } else {
            message = user.repeat();
//        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode shuffle(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message;
//        if(user.isOnline() == false) {
//            message = commandInput.getUsername() + " is offline.";
//        } else {
            message = user.shuffle(seed);
//        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode forward(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode backward(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode like(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if(user.isOnline() == false){
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

    public static ObjectNode next(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode prev(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode createPlaylist(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.createPlaylist(commandInput.getPlaylistName(), commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode addRemoveInPlaylist(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode switchVisibility(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode showPlaylists(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    public static ObjectNode follow(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode status(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    public static ObjectNode showLikedSongs(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    public static ObjectNode getPreferredGenre(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    public static ObjectNode getTop5Songs(CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    public static ObjectNode getTop5Playlists(CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    public static ObjectNode switchConnectionStatus(CommandInput commandInput){
        User user = Admin.getUser(commandInput.getUsername());
        //String username = user.getUsername();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (user != null) {
            String message = user.switchConnectionStatus();
            objectNode.put("message", message);
        } else {
            //objectNode.put("message", "The username " + username + "doesn't exist.");
            objectNode.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    public static ObjectNode getOnlineUsers(CommandInput commandInput) {
        List<String> onlineUsers = Admin.getOnlineUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result",  objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    public static ObjectNode addUser(CommandInput commandInput){
        String username = commandInput.getUsername();
        int age = commandInput.getAge();
        String city = commandInput.getCity();
        String type = commandInput.getType();
        String message = Admin.addUser(username,age,city,type);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message",message);

        return objectNode;
    }

    public static ObjectNode addAlbum(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null){
            if(user.getUserType().equals("artist")) {
                Artist artist = (Artist) user;
                String name = commandInput.getName();
                int releaseYear = commandInput.getReleaseYear();
                String description = commandInput.getDescription();
                List<Song> songs = commandInput.getSongs();
                String owner = commandInput.getUsername();

                String message = artist.addAlbum(name, releaseYear, description, songs, owner);
                objectNode.put("message", message);
            } else {
                 objectNode.put("message", "The username " + commandInput.getUsername() + " is not an artist.");
            }
        } else {
            objectNode.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }


    public static ObjectNode showAlbums(CommandInput commandInput){
        String artistName = commandInput.getUsername();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        List<ObjectNode> result = Admin.showAlbums(artistName);
        objectNode.set("result", objectMapper.valueToTree(result) );

        return  objectNode;
    }

    public static ObjectNode printCurrentPage(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Page page = PageFactory.createPage(user);
        String message;

        if (page != null) {
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

    public static ObjectNode addEvent(CommandInput commandInput){
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null){
            if(user.getUserType().equals("artist")) {
                Artist artist = (Artist)user;
                Date date = artist.parseDate(commandInput.getDate());
                String message = artist.addEvent(commandInput.getName(),commandInput.getDescription(), date);
                objectNode.put("message",message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not an artist.");
            }
        } else {
            objectNode.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    public static ObjectNode addMerch(CommandInput commandInput){
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null){
//            if(user.isArtist()) {
                Artist artist = (Artist)user;
                String message = artist.addMerch(commandInput.getName(),commandInput.getDescription(), commandInput.getPrice());
                objectNode.put("message",message);
//            }
//            else {
//                objectNode.put("message",  commandInput.getUsername() + " is not an artist.");
//            }
        } else {
            objectNode.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    public static ObjectNode getAllUsers(CommandInput commandInput){
        List<String> users = Admin.getAllUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result",objectMapper.valueToTree(users));

        return objectNode;
    }

    public static  ObjectNode deleteUser(CommandInput commandInput){
        String message = Admin.deleteUser(commandInput.getUsername());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode addPodcast(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null){
            if(user.getUserType().equals("host")) {
                Host artist = (Host) user;
                String name = commandInput.getName();
                List<Episode> episode = Admin.convertEpisodes(commandInput.getEpisodes());
                String owner = commandInput.getUsername();

                String message = artist.addPodcast(name,owner,episode);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not a host.");
            }
        } else {
            objectNode.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    public static ObjectNode addAnnouncement(CommandInput commandInput){
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if(user != null){
            if(user.getUserType().equals("host")){
                Host host = (Host) user;
                String name = commandInput.getName();
                String description = commandInput.getDescription();

                String message = host.addAnouncement(name,description);
                objectNode.put("message", message);
            } else{
                objectNode.put("message",  commandInput.getUsername() + " is not a host.");
            }
        } else {
            objectNode.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    public static ObjectNode removeAnnouncement(CommandInput commandInput){
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if(user != null){
            if(user.getUserType().equals("host")){
                Host host = (Host) user;
                String name = commandInput.getName();

                String message = host.removeAnnouncement(name);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not a host.");
            }
        } else {
            objectNode.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    public static ObjectNode showPodcasts(CommandInput commandInput){
        String hostName = commandInput.getUsername();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        List<ObjectNode> result = Admin.showPodcasts(hostName);
        objectNode.set("result", objectMapper.valueToTree(result) );

        return  objectNode;
    }

    public static ObjectNode removeAlbum(CommandInput commandInput){
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if(user != null){
            if (user.getUserType().equals("artist")){
                Artist artist = (Artist) user;
                String name = commandInput.getName();

                String message = artist.removeAlbum(name);
                objectNode.put("message", message);
            } else {
                objectNode.put("message",  commandInput.getUsername() + " is not a host.");
            }
        } else {
            objectNode.put("message", "The username " + commandInput.getUsername() + " doesn't exist.");
        }

        return objectNode;
    }

    public static ObjectNode changePage(CommandInput commandInput){
        User user = Admin.getUser(commandInput.getUsername());

        String message = user.changePage(commandInput.getNextPage());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

}
