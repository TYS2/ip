package bob;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A task that must be completed by a specified date and time. */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate end;

    /** Creates a deadline task with the supplied description and date. */
    public Deadline(String item, LocalDate end) {
        super(item);
        this.end = end;
    }

    @Override
    public String toStorageString() {
        // LocalDateTime.toString() uses a stable ISO representation.
        return "D | " + (getDone() ? "1" : "0") + " | "
                + getItem() + " | " + end;
    }

    @Override
    public String toString() {
        return "[D][" + (getDone() ? "X" : " ") + "] "
                + getItem() + " (by: " + end.format(DISPLAY_FORMAT) + ")";
    }
}
