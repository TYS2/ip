/** A task that must be completed by a specified date or time. */
public class Deadline extends Task{
    /** The date or time by which the task should be completed. */
    private final String end;

    /** Creates a deadline task with the given description and deadline. */
    public Deadline(String item, String end) {
        super(item);
        this.end = end;
    }

    /** @return the line format used when saving this deadline. */
    @Override
    public String toStorageString() {
        return "D | " + (getDone() ? "1" : "0") + " | " + getItem()
                + " | " + end;
    }

    /** @return the display form of this deadline task. */
    @Override
    public String toString() {
        return "[D][" + (getDone() ? "X" : " ") + "] "
                + getItem() + " (by: " + end + ")";
    }
}

