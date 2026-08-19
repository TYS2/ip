import java.util.ArrayList;
import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hello! I'm Bob.");
        System.out.println("What can I do for you?");

        while (true) {
            String command = scanner.nextLine();

            try {
                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }

                if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");

                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }

                    continue;
                }

                if (command.startsWith("mark ")) {
                    int taskNumber = Integer.parseInt(command.substring(5));

                    if (taskNumber < 1 || taskNumber > tasks.size()) {
                        throw new BobException("That task number does not exist.");
                    }

                    Task task = tasks.get(taskNumber - 1);
                    task.markDone();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + task);

                    continue;
                }

                if (command.startsWith("unmark ")) {
                    int taskNumber = Integer.parseInt(command.substring(7));

                    if (taskNumber < 1 || taskNumber > tasks.size()) {
                        throw new BobException("That task number does not exist.");
                    }

                    Task task = tasks.get(taskNumber - 1);
                    task.markUndone();

                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);

                    continue;
                }

                if (command.startsWith("todo")) {
                    String description = command.substring(4).trim();

                    if (description.isEmpty()) {
                        throw new BobException("The description of a todo cannot be empty.");
                    }

                    Task task = new Task(description);
                    tasks.add(task);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");

                    continue;
                }

                if (command.startsWith("deadline")) {
                    String input = command.substring(8).trim();

                    if (input.isEmpty()) {
                        throw new BobException("The deadline description cannot be empty.");
                    }

                    String[] parts = input.split(" /by ", 2);

                    if (parts.length < 2 || parts[0].trim().isEmpty()
                            || parts[1].trim().isEmpty()) {
                        throw new BobException(
                                "A deadline needs a description and a /by date."
                        );
                    }

                    String description = parts[0].trim();
                    String end = parts[1].trim();

                    Task task = new Deadline(description, end);
                    tasks.add(task);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");

                    continue;
                }

                if (command.startsWith("event")) {
                    String input = command.substring(5).trim();

                    if (input.isEmpty()) {
                        throw new BobException("The event description cannot be empty.");
                    }

                    String[] parts = input.split(" /from | /to ", 3);

                    if (parts.length < 3
                            || parts[0].trim().isEmpty()
                            || parts[1].trim().isEmpty()
                            || parts[2].trim().isEmpty()) {
                        throw new BobException(
                                "An event needs a description, /from time, and /to time."
                        );
                    }

                    String description = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();

                    Task task = new Event(description, from, to);
                    tasks.add(task);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");

                    continue;
                }

                throw new BobException("I don't understand that command.");

            } catch (BobException e) {
                System.out.println("OOPS!!! " + e.getMessage());

            } catch (NumberFormatException e) {
                System.out.println("OOPS!!! Please enter a valid task number.");
            }
        }

        scanner.close();
    }
}