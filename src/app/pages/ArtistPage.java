package app.pages;

import app.audio.Collections.Album;
import app.utils.Event;
import app.utils.Merch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ArtistPage implements Page {
    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Event> events;
    @Getter
    private ArrayList<Merch> merches;

    public ArtistPage(final ArrayList<Album> albums, final ArrayList<Event> events,
                      final ArrayList<Merch> merches) {
        this.albums = albums;
        this.events = events;
        this.merches = merches;
    }
    /**
     * Retrieves a formatted string representation of the current page's content,
     * including albums,
     * merchandise, and events, with detailed information for each item.
     *
     * @return A formatted string containing details of albums,
     * merchandise, and events on the current page.
     */
    @Override
    public String printCurrentPage() {
        List<String> albumNames = new ArrayList<>();
        for (Album album : albums) {
            albumNames.add(album.getName());
        }

        List<String> eventNames = new ArrayList<>();
        for (Event event : events) {
            String details = event.getName() + " - " + event.getDate().toString()
                    + ":\n\t" + event.getDescription();
            eventNames.add(details);
        }

        List<String> merchNames = new ArrayList<>();
        for (Merch merch : merches) {
            String details = merch.getName() + " - " + merch.getPrice()
                    + ":\n\t" + merch.getDescription();
            merchNames.add(details);
        }

        return "Albums:\n\t" + albumNames + "\n\nMerch:\n\t"
                + merchNames + "\n\nEvents:\n\t" + eventNames;
    }

}
