import java.util.ArrayList;
import java.util.Scanner;

public class Bob {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hello! I'm Bob.");
        System.out.println("What can I do for you?");

        mainLoop:
        while (true) {
            String command = scanner.nextLine();

            try {
                CommandType commandType = getCommandType(command);

                switch (commandType) {

                    case BYE:
                        System.out.println("Bye. Hope to see you again soon!");
                        break mainLoop;

                    case LIST:
                        listTasks(tasks);
                        break;

                    case DELETE:
                        deleteTask(command, tasks);
                        break;

                    case MARK:
                        markTask(command, tasks);
                        break;

                    case UNMARK:
                        unmarkTask(command, tasks);
                        break;

                    case TODO:
                        addTodo(command, tasks);
                        break;

                    case DEADLINE:
                        addDeadline(command, tasks);
                        break;

                    case EVENT:
                        addEvent(command, tasks);
                        break;

                    case UNKNOWN:
                        throw new BobException(
                                "I don't understand that command."
                        );
                }

            } catch (BobException e) {
                System.out.println("OOPS!!! " + e.getMessage());

            } catch (NumberFormatException e) {
                System.out.println(
                        "OOPS!!! Please enter a valid task number."
                );
            }
        }

        scanner.close();
    }

    /**
     * Determines which command the user entered.
     */
    public static CommandType getCommandType(String command) {
        String keyword = command.split(" ", 2)[0];

        switch (keyword) {
            case "bye":
                return CommandType.BYE;

            case "list":
                return CommandType.LIST;

            case "delete":
                return CommandType.DELETE;

            case "mark":
                return CommandType.MARK;

            case "unmark":
                return CommandType.UNMARK;

            case "todo":
                return CommandType.TODO;

            case "deadline":
                return CommandType.DEADLINE;

            case "event":
                return CommandType.EVENT;

            default:
                return CommandType.UNKNOWN;
        }
    }

    /**
     * Displays all tasks.
     */
    public static void listTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Deletes a task.
     */
    public static void deleteTask(
            String command,
            ArrayList<Task> tasks) throws BobException {

        String input = command.substring(6).trim();

        if (input.isEmpty()) {
            throw new BobException(
                    "Please specify which task you want to delete."
            );
        }

        int taskNumber = Integer.parseInt(input);

        checkTaskNumber(taskNumber, tasks);

        Task task = tasks.remove(taskNumber - 1);

        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println(
                "Now you have " + tasks.size() + " tasks in the list."
        );
    }

    /**
     * Marks a task as done.
     */
    public static void markTask(
            String command,
            ArrayList<Task> tasks) throws BobException {

        String input = command.substring(4).trim();

        if (input.isEmpty()) {
            throw new BobException(
                    "Please specify which task you want to mark."
            );
        }

        int taskNumber = Integer.parseInt(input);

        checkTaskNumber(taskNumber, tasks);

        Task task = tasks.get(taskNumber - 1);
        task.markDone();

        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Marks a task as not done.
     */
    public static void unmarkTask(
            String command,
            ArrayList<Task> tasks) throws BobException {

        String input = command.substring(6).trim();

        if (input.isEmpty()) {
            throw new BobException(
                    "Please specify which task you want to unmark."
            );
        }

        int taskNumber = Integer.parseInt(input);

        checkTaskNumber(taskNumber, tasks);

        Task task = tasks.get(taskNumber - 1);
        task.markUndone();

        System.out.println(
                "OK, I've marked this task as not done yet:"
        );
        System.out.println("  " + task);
    }

    /**
     * Adds a todo task.
     */
    public static void addTodo(
            String command,
            ArrayList<Task> tasks) throws BobException {

        String description = command.substring(4).trim();

        if (description.isEmpty()) {
            throw new BobException(
                    "The description of a todo cannot be empty."
            );
        }

        Task task = new Task(description);
        tasks.add(task);

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println(
                "Now you have " + tasks.size() + " tasks in the list."
        );
    }

    /**
     * Adds a deadline task.
     */
    public static void addDeadline(
            String command,
            ArrayList<Task> tasks) throws BobException {

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

        Task task = new Deadline(description, end);
        tasks.add(task);

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println(
                "Now you have " + tasks.size() + " tasks in the list."
        );
    }

    /**
     * Adds an event task.
     */
    public static void addEvent(
            String command,
            ArrayList<Task> tasks) throws BobException {

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

        Task task = new Event(description, from, to);
        tasks.add(task);

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println(
                "Now you have " + tasks.size() + " tasks in the list."
        );
    }

    /**
     * Checks whether a task number is valid.
     */
    public static void checkTaskNumber(
            int taskNumber,
            ArrayList<Task> tasks) throws BobException {

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new BobException(
                    "That task number does not exist."
            );
        }
    }
}