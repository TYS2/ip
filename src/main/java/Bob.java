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
                Task task = tasks.get(taskNumber - 1);

                task.markDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + task);

                continue;
            }

            if (command.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(command.substring(7));
                Task task = tasks.get(taskNumber - 1);

                task.markUndone();

                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + task);

                continue;
            }

            if (command.startsWith("todo ")) {
                String description = command.substring(5);

                Task task = new Task(description);
                tasks.add(task);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");

                continue;
            }

            if (command.startsWith("deadline ")) {
                String input = command.substring(9);

                String[] parts = input.split(" /by ", 2);

                String description = parts[0];
                String end = parts[1];

                Task task = new Deadline(description, end);
                tasks.add(task);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");

                continue;
            }

            if (command.startsWith("event ")) {
                String input = command.substring(6);

                String[] parts = input.split(" /from | /to ", 3);

                String description = parts[0];
                String from = parts[1];
                String to = parts[2];

                Task task = new Event(description, from, to);
                tasks.add(task);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");

                continue;
            }

            System.out.println("I don't understand that command.");
        }

        scanner.close();
    }
}