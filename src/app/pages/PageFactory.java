package app.pages;

import app.audio.LibraryEntry;
import app.user.User;
import app.utils.ArtistsEntry;
import app.utils.HostEntry;

public class PageFactory {
    public static Page createPage(User user) {
        if (user.isOnline()) {
            LibraryEntry libraryUser = user.getSearchBar().getLastSelectedUser();
            if (libraryUser != null) {
                if (libraryUser.isArtistEntry()) {
                    ArtistsEntry artist = (ArtistsEntry) libraryUser;
                    return new ArtistPage(artist.getAlbums(), artist.getEvents(), artist.getMerches());
                } else if (libraryUser.isHostEntry()) {
                    HostEntry host = (HostEntry) libraryUser;
                    return new HostPage(host.getPodcasts(), host.getAnnouncements());
                }
            } else {
                if(user.getPageType().equals("LikedContent")){
                    return new LikedContentPage(user.getLikedSongs(), user.getFollowedPlaylists());
                } else if (user.getPageType().equals("Home")) {
                    return new HomePage(user.getLikedSongs(), user.getFollowedPlaylists());
                }
            }
        }
        return null;
    }
}
