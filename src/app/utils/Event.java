package app.utils;

public class Event {
    private String name;
    private String description;
    private Date date;

    public Event(final String name, final String description, final Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    /**
     * Gets the name of the event.
     *
     * @return The name of the event.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the name of the event.
     *
     * @return The name of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the name of the event.
     *
     * @return The name of the event.
     */
    public Date getDate() {
        return date;
    }
}
