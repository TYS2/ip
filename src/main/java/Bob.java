import java.util.ArrayList;
import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> arr = new ArrayList<>();
        int size=0;

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

            if (command.equals("list")) {
                for (int i=0; i<size; i++) {
                    System.out.printf("%d" + " " + "%s" + "\n", i + 1, arr.get(i));
                }
                continue;
            }

            size+=1;
            arr.add(command);
            System.out.println("added: "+ command);
        }

        scanner.close();
    }
}