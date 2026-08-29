package bob;

/** Represents a basic todo task with a description and completion state. */

public class Task {
    /** The task description shown to the user. */
    private final String item;
    /** Whether the task has been marked as completed. */
    private boolean done;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param item Task description.
     */
    public Task(String item) {
        this.item = item;
        this.done = false;
    }

    /**
     * Marks this task as completed.
     */
    public void markDone() {
        this.done = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void markUndone() {
        this.done = false;
    }

    /**
     * Returns whether this task is completed.
     *
     * @return Whether this task is completed.
     */
    public boolean getDone() {
        return this.done;
    }

    /**
     * Returns this task's description.
     *
     * @return Task description.
     */
    public String getItem() {
        return this.item;
    }

    /**
     * Returns the line format used when saving this task.
     *
     * @return Storage line.
     */
    public String toStorageString() {
        return "T | " + (done ? "1" : "0") + " | " + item;
    }

    /**
     * Returns the display form of this task.
     *
     * @return Display form of this task.
     */
    @Override
    public String toString() {
        return "[T][" + (done ? "X" : " ") + "] " + item;
    }
}
