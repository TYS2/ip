package Bob;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A task representing an event with a start and end time. */
public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Creates an event with a start and end date.
     *
     * @param item Event description.
     * @param from Event start date.
     * @param to Event end date.
     */
    public Event(String item, LocalDate from, LocalDate to) {
        super(item);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the storage representation of this event.
     *
     * @return Storage line.
     */
    @Override
    public String toStorageString() {
        return "E | " + (getDone() ? "1" : "0") + " | " + getItem()
                + " | " + from + " | " + to;
    }

    /**
     * Returns the display representation of this event.
     *
     * @return Display form of this event.
     */
    @Override
    public String toString() {
        return "[E][" + (getDone() ? "X" : " ") + "] " + getItem()
                + " (from: " + from.format(DISPLAY_FORMAT)
                + " to: " + to.format(DISPLAY_FORMAT) + ")";
    }
}
