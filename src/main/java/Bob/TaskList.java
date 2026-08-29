package bob;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    /** Returns the tasks for display in their current order. */
    public ArrayList<Task> listTasks() {
        return asList();
    }

    /** Removes a one-based task and returns it. */
    public Task deleteTask(int taskNumber) throws BobException {
        checkTaskNumber(taskNumber);
        return remove(taskNumber - 1);
    }

    /** Marks a one-based task as done and returns it. */
    public Task markTask(int taskNumber) throws BobException {
        checkTaskNumber(taskNumber);
        Task task = get(taskNumber - 1);
        task.markDone();
        return task;
    }

    /** Marks a one-based task as not done and returns it. */
    public Task unmarkTask(int taskNumber) throws BobException {
        checkTaskNumber(taskNumber);
        Task task = get(taskNumber - 1);
        task.markUndone();
        return task;
    }

    /** Creates and adds a todo task. */
    public Task addTodo(String description) throws BobException {
        if (description.isEmpty()) {
            throw new BobException("The description of a todo cannot be empty.");
        }
        Task task = new Task(description);
        add(task);
        return task;
    }

    /** Creates and adds a deadline from its description and date input. */
    public Task addDeadline(String input) throws BobException {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2) {
            throw new BobException("A deadline needs a description and a /by date.");
        }
        String description = parts[0].trim();
        String end = parts[1].trim();
        if (description.isEmpty()) {
            throw new BobException("The deadline description cannot be empty.");
        }
        if (end.isEmpty()) {
            throw new BobException("The deadline date cannot be empty.");
        }
        try {
            Task task = new Deadline(description, LocalDate.parse(end));
            add(task);
            return task;
        } catch (DateTimeParseException e) {
            throw new BobException("Please enter the date as yyyy-MM-dd, "
                    + "for example 2019-10-15.");
        }
    }

    /** Creates and adds an event from its description and date inputs. */
    public Task addEvent(String input) throws BobException {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new BobException("An event needs a description, /from, and /to.");
        }
        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        if (description.isEmpty()) {
            throw new BobException("The event description cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new BobException("The event start time cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new BobException("The event end time cannot be empty.");
        }
        try {
            Task task = new Event(description, LocalDate.parse(from), LocalDate.parse(to));
            add(task);
            return task;
        } catch (DateTimeParseException e) {
            throw new BobException("Please enter dates as yyyy-MM-dd, "
                    + "for example 2019-10-15.");
        }
    }

    /** Executes a non-exit command against this task list. */
    public void execute(String command, CommandType type, Ui ui, Storage storage)
            throws BobException {
        switch (type) {
            case LIST:
                ui.show("Here are the tasks in your list:");
                ArrayList<Task> listed = listTasks();
                for (int i = 0; i < listed.size(); i++) {
                    ui.show((i + 1) + "." + listed.get(i));
                }
                break;
            case DELETE:
                Task deleted = deleteTask(Integer.parseInt(command.substring(6).trim()));
                storage.save(asList());
                ui.show("Noted. I've removed this task:");
                ui.show("  " + deleted);
                ui.show("Now you have " + size() + " tasks in the list.");
                break;
            case MARK:
                Task marked = markTask(Integer.parseInt(command.substring(4).trim()));
                storage.save(asList());
                ui.show("Nice! I've marked this task as done:");
                ui.show("  " + marked);
                break;
            case UNMARK:
                Task unmarked = unmarkTask(Integer.parseInt(command.substring(6).trim()));
                storage.save(asList());
                ui.show("OK, I've marked this task as not done yet:");
                ui.show("  " + unmarked);
                break;
            case TODO:
                showAdded(addTodo(command.substring(4).trim()), ui, storage);
                break;
            case DEADLINE:
                showAdded(addDeadline(command.substring(8).trim()), ui, storage);
                break;
            case EVENT:
                showAdded(addEvent(command.substring(5).trim()), ui, storage);
                break;
            default: throw new BobException("I don't understand that command.");
        }
    }

    private void showAdded(Task task, Ui ui, Storage storage) throws BobException {
        storage.save(asList());
        ui.show("Got it. I've added this task:");
        ui.show("  " + task);
        ui.show("Now you have " + size() + " tasks in the list.");
    }
}
