package app.pages;

public interface Page {

    /**
     * Retrieves a formatted string representation of the current page's content.
     * The actual content and format depend on the implementing class or subclass.
     *
     * @return A formatted string containing details specific to the current page.
     */
    String printCurrentPage();
}
