package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.utils.Announcement;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Host extends User{

    @Getter
    private ArrayList<Podcast> podcasts;
    @Getter
    private ArrayList<Announcement> announcements;

    public Host(String username, int age, String city) {
        super(username, age, city);
        this.setUserType("host");
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
    }

    public Podcast getPodcast(String name){
        for(Podcast podcast : podcasts){
            if(podcast.getName().equals(name)){
                return podcast;
            }
        }
        return null;
    }

    public String addPodcast(String name, String owner, List<Episode> episodes){
        Podcast podcast = getPodcast(name);
        if(podcasts.contains(podcast)){
            return getUsername() + " has another podcast with the same name.";
        } else {
            Set<String> uniqueEpisodenames = new HashSet<>();
            for (Episode episode : episodes) {
                if (!uniqueEpisodenames.add(episode.getName())) {
                    return getUsername() + " has another episode with the same name.";
                }
            }

            Podcast newPodcast = new Podcast(name,owner,episodes);
            podcasts.add(newPodcast);
            Admin.addPodcast(newPodcast);

            return getUsername() + " has added new podcast successfully.";
        }
    }

    public String addAnouncement(String name, String description){
        if(hasAnnouncement(name)){
            return getUsername() + "  has already added an announcement with this name.";
        }

        Announcement newAnnouncement = new Announcement(name, description);
        announcements.add(newAnnouncement);
        return getUsername() + " has successfully added new announcement.";
    }

    public String removeAnnouncement(String name){
        if(hasAnnouncement(name)){
            for(Announcement announcement : announcements){
                if(announcement.getName().equals(name)){
                    announcements.remove(announcement);
                }
            }
            return getUsername() + " has successfully deleted the announcement.";
        }

        return getUsername() + " has no announcement with the given name.";
    }

    public String removePodcast(String name){
        Podcast podcast = getPodcast(name);
        if(hasPodcast(name)){
            List<User> users = Admin.getUsers();
            for(User user : users){
                Podcast sourcePodcast = new Podcast("","",null);
                if(user.getPlayer().getSource() != null){
                    sourcePodcast = (Podcast) user.getPlayer().getSource().getAudioCollection();
                }
                if(sourcePodcast.getName().equals(name)){
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

    public boolean hasAnnouncement(String name){
        for(Announcement announcement : announcements){
            if(announcement.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean hasPodcast(String name){
        for(Podcast podcast : podcasts){
            if(podcast.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}

