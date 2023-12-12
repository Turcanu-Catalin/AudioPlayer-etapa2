package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.utils.Announcement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Host extends User {

    @Getter
    private ArrayList<Podcast> podcasts;
    @Getter
    private ArrayList<Announcement> announcements;

    public Host(final String username, final int age, final String city) {
        super(username, age, city);
        this.setUserType("host");
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
    }

    /**
     * Retrieves the podcast with the specified name.
     *
     * @param name The name of the podcast.
     * @return The podcast with the specified name, or null if not found.
     */
    public Podcast getPodcast(final String name) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
    }

    /**
     * Adds a new podcast with the specified details to the host's collection.
     *
     * @param name     The name of the podcast.
     * @param owner    The owner of the podcast.
     * @param episodes The list of episodes in the podcast.
     * @return A status message indicating the success or failure of the operation.
     */
    public String addPodcast(final String name, final String owner,
                             final List<Episode> episodes) {
        Podcast podcast = getPodcast(name);
        if (podcasts.contains(podcast)) {
            return getUsername() + " has another podcast with the same name.";
        } else {
            Set<String> uniqueEpisodenames = new HashSet<>();
            for (Episode episode : episodes) {
                if (!uniqueEpisodenames.add(episode.getName())) {
                    return getUsername() + " has another episode with the same name.";
                }
            }

            Podcast newPodcast = new Podcast(name, owner, episodes);
            podcasts.add(newPodcast);
            Admin.addPodcast(newPodcast);

            return getUsername() + " has added new podcast successfully.";
        }
    }

    /**
     * Adds a new announcement with the specified details to the host's collection.
     *
     * @param name        The name of the announcement.
     * @param description The description of the announcement.
     * @return A status message indicating the success or failure of the operation.
     */
    public String addAnouncement(final String name, final String description) {
        if (hasAnnouncement(name)) {
            return getUsername() + "  has already added an announcement with this name.";
        }

        Announcement newAnnouncement = new Announcement(name, description);
        announcements.add(newAnnouncement);
        return getUsername() + " has successfully added new announcement.";
    }

    /**
     * Removes the announcement with the specified name from the host's collection.
     *
     * @param name The name of the announcement to be removed.
     * @return A status message indicating the success or failure of the operation.
     */
    public String removeAnnouncement(final String name) {
        if (hasAnnouncement(name)) {
            for (Announcement announcement : announcements) {
                if (announcement.getName().equals(name)) {
                    announcements.remove(announcement);
                }
            }
            return getUsername() + " has successfully deleted the announcement.";
        }

        return getUsername() + " has no announcement with the given name.";
    }

    /**
     * Removes the podcast with the specified name from the host's collection.
     *
     * @param name The name of the podcast to be removed.
     * @return A status message indicating the success or failure of the operation.
     */
    public String removePodcast(final String name) {
        Podcast podcast = getPodcast(name);
        if (hasPodcast(name)) {
            List<User> users = Admin.getUsers();
            for (User user : users) {
                Podcast sourcePodcast = new Podcast("", "", null);
                if (user.getPlayer().getSource() != null) {
                    sourcePodcast = (Podcast) user.getPlayer().getSource().getAudioCollection();
                }
                if (sourcePodcast.getName().equals(name)) {
                    return getUsername() + " can't delete this podcast.";
                }
            }

            podcasts.remove(podcast);
            Admin.deletePodcast(podcast);
            return getUsername() + " deleted the podcast successfully.";

        } else {
            return getUsername() + " doesn't have a podcast with the given name.";
        }

    }

    /**
     * Checks if the host has an announcement with the specified name.
     *
     * @param name The name of the announcement.
     * @return True if the host has an announcement with the specified name, otherwise false.
     */
    public boolean hasAnnouncement(final String name) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the host has a podcast with the specified name.
     *
     * @param name The name of the podcast.
     * @return True if the host has a podcast with the specified name, otherwise false.
     */
    public boolean hasPodcast(final String name) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

