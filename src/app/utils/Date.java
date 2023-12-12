package app.utils;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(final int day, final int month, final int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Gets the day component of the date.
     *
     * @return The day component of the date.
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets the month component of the date.
     *
     * @return The month component of the date.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the year component of the date.
     *
     * @return The year component of the date.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns a string representation of the date in the format "DD-MM-YYYY".
     *
     * @return A string representation of the date.
     */
    @Override
    public String toString() {
        return String.format("%02d-%02d-%04d", day, month, year);
    }

}
