package fileio.input;

import app.audio.Files.Episode;

import java.util.ArrayList;
import java.util.List;

public final class PodcastInput {
    private String name;
    private String owner;
    private List<EpisodeInput> episodes;

    public PodcastInput(){

    }

    public PodcastInput(String name, String owner, List<EpisodeInput> episodes) {
        this.name = name;
        this.owner = owner;
        this.episodes = episodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public List<EpisodeInput> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "PodcastInput{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", episodes=" + episodes +
                '}';
    }
}
