/** A task representing an event with a start and end time. */
public class Event extends Task {
    /** The event start time. */
    private final String from;
    /** The event end time. */
    private final String to;

    /** Creates an event task with the given description and time range. */
    public Event(String item, String from, String to) {
        super(item);
        this.from = from;
        this.to = to;
    }

    /** @return the line format used when saving this event. */
    @Override
    public String toStorageString() {
        return "E | " + (getDone() ? "1" : "0") + " | " + getItem()
                + " | " + from + " | " + to;
    }

    /** @return the display form of this event task. */
    @Override
    public String toString() {
        return "[E][" + (getDone() ? "X" : " ") + "] "
                + getItem() + " (from: " + from + " to: " + to + ")";
    }
}
