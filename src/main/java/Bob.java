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

            Task task = new Task(command);
            tasks.add(task);

            System.out.println("added: " + task);
        }

        scanner.close();
    }

}