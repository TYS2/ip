package bob;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A task that must be completed by a specified date and time. */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate end;

    /**
     * Creates a deadline with a description and due date.
     *
     * @param item Deadline description.
     * @param end Deadline due date.
     */
    public Deadline(String item, LocalDate end) {
        super(item);
        this.end = end;
    }

    /**
     * Returns the storage representation of this deadline.
     *
     * @return Storage line.
     */
    @Override
    public String toStorageString() {
        // LocalDateTime.toString() uses a stable ISO representation.
        return "D | " + (getDone() ? "1" : "0") + " | "
                + getItem() + " | " + end;
    }

    /**
     * Returns the display representation of this deadline.
     *
     * @return Display form of this deadline.
     */
    @Override
    public String toString() {
        return "[D][" + (getDone() ? "X" : " ") + "] "
                + getItem() + " (by: " + end.format(DISPLAY_FORMAT) + ")";
    }
}
