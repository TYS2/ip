import java.util.ArrayList;

/** Owns the tasks currently managed by the application. */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Returns the task at a zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Adds a task to the end of the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Removes and returns the task at a zero-based index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /** Returns the number of tasks. */
    public int size() {
        return tasks.size();
    }

    /** Validates a one-based task number. */
    public void checkTaskNumber(int taskNumber) throws BobException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new BobException("That task number does not exist.");
        }
    }

    /** Returns a copy suitable for persistence. */
    public ArrayList<Task> asList() {
        return new ArrayList<>(tasks);
    }
}
