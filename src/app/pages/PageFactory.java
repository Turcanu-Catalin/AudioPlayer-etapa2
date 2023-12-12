package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.user.User;
import app.utils.ArtistsEntry;
import app.utils.HostEntry;

import java.util.ArrayList;

public class PageFactory {
    private static PageFactory instance;

    private PageFactory() {
    }

    /**
     * Gets the singleton instance of the PageFactory.
     *
     * @return The singleton instance of the PageFactory.
     */
    public static PageFactory getInstance() {
        if (instance == null) {
            instance = new PageFactory();
        }
        return instance;
    }

    /**
     * Creates and returns a Page based on the provided user's information.
     *
     * @param user The user for whom to create a page.
     * @return A Page object representing the content tailored to the user's information,
     * or null if the user is offline or the last selected user is null.
     */
    public static Page createPage(final User user) {
        if (user.isOnline()) {
            LibraryEntry libraryUser = user.getSearchBar().getLastSelectedUser();

            if (libraryUser != null) {
                if (libraryUser.isArtistEntry()) {
                    ArtistsEntry artist = (ArtistsEntry) libraryUser;
                    return new ArtistPage(artist.getAlbums(),
                            artist.getEvents(), artist.getMerches());
                } else if (libraryUser.isHostEntry()) {
                    HostEntry host = (HostEntry) libraryUser;
                    return new HostPage(host.getPodcasts(),
                            host.getAnnouncements());
                }
            } else {
                ArrayList<Song> userLikedSongs = new ArrayList<>(user.getLikedSongs());
                ArrayList<Playlist> userFollowedPlaylists = new ArrayList<>(user.getFollowedPlaylists());

                if (user.getPageType().equals("LikedContent")) {
                    return new LikedContentPage(userLikedSongs, userFollowedPlaylists);
                } else if (user.getPageType().equals("Home")) {
                    return new HomePage(userLikedSongs, userFollowedPlaylists);
                }
            }
        }
        return null;
    }
}
