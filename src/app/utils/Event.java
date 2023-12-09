package app.utils;

public class Event {
    private String name;
    private String description;
    private Date date;

    public Event(String name, String description, Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
}
