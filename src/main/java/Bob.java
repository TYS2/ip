import java.time.format.DateTimeParseException;
import java.time.LocalDate;

/**
 * Class containing the chatbot
 */
public class Bob {

    /**
     * Starts the interactive task-manager application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        TaskList tasks;

        try {
            tasks = new TaskList(TaskStorage.load());
        } catch (BobException e) {
            tasks = new TaskList();
            ui.showError(e.getMessage());
        }

        ui.showWelcome();

        mainLoop:
        while (true) {
            String command = ui.readCommand();

            try {
                CommandType commandType = parser.parse(command);

                switch (commandType) {

                    case BYE:
                        ui.show("Bye. Hope to see you again soon!");
                        break mainLoop;

                    case LIST:
                        listTasks(tasks, ui);
                        break;

                    case DELETE:
                        deleteTask(command, tasks, ui);
                        break;

                    case MARK:
                        markTask(command, tasks, ui);
                        break;

                    case UNMARK:
                        unmarkTask(command, tasks, ui);
                        break;

                    case TODO:
                        addTodo(command, tasks, ui);
                        break;

                    case DEADLINE:
                        addDeadline(command, tasks, ui);
                        break;

                    case EVENT:
                        addEvent(command, tasks, ui);
                        break;

                    case UNKNOWN:
                        throw new BobException(
                                "I don't understand that command."
                        );
                }

            } catch (BobException e) {
                ui.showError(e.getMessage());

            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid task number.");
            }
        }

        ui.close();
    }

    /**
     * Displays all tasks in their current order.
     *
     * @param tasks the tasks to display
     */
    public static void listTasks(TaskList tasks, Ui ui) {
        ui.show("Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            ui.show((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Deletes the task selected by the command and reports the result.
     *
     * @param command a delete command containing a one-based task number
     * @param tasks the list from which the task is removed
     * @throws BobException if the command does not specify a valid task
     */
    public static void deleteTask(
            String command,
            TaskList tasks,
            Ui ui) throws BobException {

        String input = command.substring(6).trim();

        if (input.isEmpty()) {
            throw new BobException(
                    "Please specify which task you want to delete."
            );
        }

        int taskNumber = Integer.parseInt(input);

        tasks.checkTaskNumber(taskNumber);

        Task task = tasks.remove(taskNumber - 1);
        TaskStorage.save(tasks.asList());

        ui.show("Noted. I've removed this task:");
        ui.show("  " + task);
        ui.show(
                "Now you have " + tasks.size() + " tasks in the list."
        );
    }

    /**
     * Marks the task selected by the command as done.
     *
     * @param command a mark command containing a one-based task number
     * @param tasks the list containing the task
     * @throws BobException if the command does not specify a valid task
     */
    public static void markTask(
            String command,
            TaskList tasks,
            Ui ui) throws BobException {

        String input = command.substring(4).trim();

        if (input.isEmpty()) {
            throw new BobException(
                    "Please specify which task you want to mark."
            );
        }

        int taskNumber = Integer.parseInt(input);

        tasks.checkTaskNumber(taskNumber);

        Task task = tasks.get(taskNumber - 1);
        task.markDone();
        TaskStorage.save(tasks.asList());

        ui.show("Nice! I've marked this task as done:");
        ui.show("  " + task);
    }

    /**
     * Marks the task selected by the command as not done.
     *
     * @param command an unmark command containing a one-based task number
     * @param tasks the list containing the task
     * @throws BobException if the command does not specify a valid task
     */
    public static void unmarkTask(
            String command,
            TaskList tasks,
            Ui ui) throws BobException {

        String input = command.substring(6).trim();

        if (input.isEmpty()) {
            throw new BobException(
                    "Please specify which task you want to unmark."
            );
        }

        int taskNumber = Integer.parseInt(input);

        tasks.checkTaskNumber(taskNumber);

        Task task = tasks.get(taskNumber - 1);
        task.markUndone();
        TaskStorage.save(tasks.asList());

        ui.show(
                "OK, I've marked this task as not done yet:"
        );
        ui.show("  " + task);
    }

    /**
     * Creates and adds a todo task from the command.
     *
     * @param command a todo command containing the task description
     * @param tasks the list to which the new task is added
     * @throws BobException if the description is empty
     */
    public static void addTodo(
            String command,
            TaskList tasks,
            Ui ui) throws BobException {

        String description = command.substring(4).trim();

        if (description.isEmpty()) {
            throw new BobException(
                    "The description of a todo cannot be empty."
            );
        }

        Task task = new Task(description);
        tasks.add(task);
        TaskStorage.save(tasks.asList());

        ui.show("Got it. I've added this task:");
        ui.show("  " + task);
        ui.show(
                "Now you have " + tasks.size() + " tasks in the list."
        );
    }

    /**
     * Creates and adds a deadline task from the command.
     *
     * @param command a deadline command containing a description and date
     * @param tasks the list to which the new task is added
     * @throws BobException if the command is missing a description or date
     */
    public static void addDeadline(
            String command,
            TaskList tasks,
            Ui ui) throws BobException {

        String input = command.substring(8).trim();

        if (input.isEmpty()) {
            throw new BobException(
                    "The deadline description cannot be empty."
            );
        }

        String[] parts = input.split(" /by ", 2);

        if (parts.length < 2) {
            throw new BobException(
                    "A deadline needs a description and a /by date."
            );
        }

        String description = parts[0].trim();
        String end = parts[1].trim();

        if (description.isEmpty()) {
            throw new BobException(
                    "The deadline description cannot be empty."
            );
        }

        if (end.isEmpty()) {
            throw new BobException(
                    "The deadline date cannot be empty."
            );
        }

        try {
            LocalDate endDate = LocalDate.parse(end);
            Task task = new Deadline(description, endDate);
            tasks.add(task);
            TaskStorage.save(tasks.asList());

            ui.show("Got it. I've added this task:");
            ui.show("  " + task);
            ui.show(
                    "Now you have " + tasks.size() + " tasks in the list."
            );
        } catch (DateTimeParseException e) {
            throw new BobException(
                    "Please enter the date as yyyy-MM-dd, "
                            + "for example 2019-10-15.");
        }
    }

    /**
     * Creates and adds an event task from the command.
     *
     * @param command an event command containing a description and time range
     * @param tasks the list to which the new task is added
     * @throws BobException if the command is missing event details
     */
    public static void addEvent(
            String command,
            TaskList tasks,
            Ui ui) throws BobException {

        String input = command.substring(5).trim();

        if (input.isEmpty()) {
            throw new BobException(
                    "The event description cannot be empty."
            );
        }

        String[] parts = input.split(" /from | /to ", 3);

        if (parts.length < 3) {
            throw new BobException(
                    "An event needs a description, /from, and /to."
            );
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty()) {
            throw new BobException(
                    "The event description cannot be empty."
            );
        }

        if (from.isEmpty()) {
            throw new BobException(
                    "The event start time cannot be empty."
            );
        }

        if (to.isEmpty()) {
            throw new BobException(
                    "The event end time cannot be empty."
            );
        }

        try {
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);

            Task task = new Event((description), fromDate, toDate);
            tasks.add(task);
            TaskStorage.save(tasks.asList());

            ui.show("Got it. I've added this task:");
            ui.show("  " + task);
            ui.show(
                    "Now you have " + tasks.size() + " tasks in the list."
            );
        } catch (DateTimeParseException e) {
            throw new BobException(
                    "Please enter dates as yyyy-MM-dd, "
                            + "for example 2019-10-15.");
        }

    }

    /**
     * Checks whether a one-based task number refers to an existing task.
     *
     * @param taskNumber the one-based task number to validate
     * @param tasks the list of available tasks
     * @throws BobException if the task number is outside the list bounds
     */
}
