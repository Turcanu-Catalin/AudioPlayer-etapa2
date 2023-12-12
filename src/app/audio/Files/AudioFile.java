package app.audio.Files;

import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioFile extends LibraryEntry {
    private final Integer duration;

    public AudioFile(final String name, final Integer duration) {
        super(name);
        this.duration = duration;
    }

    /**
     * Indicates whether the current audio file represents a song.
     *
     * @return False, as this class is not a song.
     */
    public boolean isSong() {
        return false;
    }

}
