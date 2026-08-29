package bob;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A task representing an event with a start and end time. */
public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate from;
    private final LocalDate to;

    /** Creates an event task with the supplied description and dates. */
    public Event(String item, LocalDate from, LocalDate to) {
        super(item);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toStorageString() {
        return "E | " + (getDone() ? "1" : "0") + " | " + getItem()
                + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E][" + (getDone() ? "X" : " ") + "] " + getItem()
                + " (from: " + from.format(DISPLAY_FORMAT)
                + " to: " + to.format(DISPLAY_FORMAT) + ")";
    }
}
