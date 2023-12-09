package app.pages;

import app.audio.Collections.Podcast;
import app.utils.Announcement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class HostPage implements Page {
    @Getter
    private ArrayList<Podcast> podcasts;
    @Getter
    private ArrayList<Announcement> announcements;

    public HostPage(ArrayList<Podcast> podcasts, ArrayList<Announcement> announcements) {
        this.podcasts = podcasts;
        this.announcements = announcements;
    }
    @Override
    public String printCurrentPage() {
        List<String> podcastDetails = new ArrayList<>();

        for (Podcast podcast : podcasts) {
            List<String> episodeDetails = new ArrayList<>();

            for (int i = 0; i < podcast.getEpisodes().size(); i++) {
                episodeDetails.add(podcast.getEpisodes().get(i).getName() + " - " +
                        podcast.getEpisodes().get(i).getDescription());
            }

            String podcastDetail =  podcast.getName() + ":\n\t[" + String.join(", ", episodeDetails) + "]\n";
            podcastDetails.add(podcastDetail);
        }

        List<String> announcementDetails = new ArrayList<>();

        for (Announcement announcement : announcements) {
            String announcementDetail = "[" + announcement.getName() + ":\n\t" + announcement.getDescription() + "\n]";
            announcementDetails.add(announcementDetail);
        }

        return "Podcasts:\n\t[" + String.join(", ", podcastDetails) + "]\n\nAnnouncements:\n\t" +
                String.join(",\n\t", announcementDetails);
    }



}
