import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String message = "Hello! I'm Bob.\n"
                + "What can I do for you? \n"
                + "Bye. Hope to see you again soon!";
        System.out.println(message);

        while (true) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            System.out.println(command);
        }

        scanner.close();
    }
}