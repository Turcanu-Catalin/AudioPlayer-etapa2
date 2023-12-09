package app.pages;

import app.audio.Collections.Album;
import app.utils.Event;
import app.utils.Merch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ArtistPage implements Page{
    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Event> events;
    @Getter
    private ArrayList<Merch> merches;

    public ArtistPage(ArrayList<Album> albums, ArrayList<Event> events, ArrayList<Merch> merches){
        this.albums = albums;
        this.events = events;
        this.merches = merches;
    }
    @Override
    public String printCurrentPage() {
        List<String> albumNames = new ArrayList<>();
        for (Album album : albums){
            albumNames.add(album.getName());
        }

        List<String> eventNames = new ArrayList<>();
        for (Event event : events){
            String details = event.getName() + " - " + event.getDate().toString() + ":\n\t" + event.getDescription();
            eventNames.add(details);
        }

        List<String> merchNames = new ArrayList<>();
        for (Merch merch : merches){
            String details = merch.getName() + " - " + merch.getPrice() + ":\n\t" + merch.getDescription();
            merchNames.add(details);
        }

        return "Albums:\n\t" + albumNames + "\n\nMerch:\n\t" + merchNames + "\n\nEvents:\n\t" + eventNames;
    }

}
