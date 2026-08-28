package Bob;

/** Represents a basic todo task with a description and completion state. */

public class Task {
    /** The task description shown to the user. */
    private final String item;
    /** Whether the task has been marked as completed. */
    private boolean done;

    /** Creates an incomplete task with the given description. */
    public Task(String item) {
        this.item = item;
        this.done = false;
    }

    /** Marks this task as completed. */
    public void markDone() {
        this.done = true;
    }

    /** Marks this task as incomplete. */
    public void markUndone() {
        this.done = false;
    }

    /** @return whether this task is completed. */
    public boolean getDone() {
        return this.done;
    }

    /** @return this task's description. */
    public String getItem() {
        return this.item;
    }

    /** @return the line format used when saving this task. */
    public String toStorageString() {
        return "T | " + (done ? "1" : "0") + " | " + item;
    }

    /** @return the display form of this task. */
    @Override
    public String toString() {
        return "[T][" + (done ? "X" : " ") + "] " + item;
    }
}
