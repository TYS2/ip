package Bob;
/** Coordinates the task manager components. */
public class Bob {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /** Creates Bob using the supplied task data file. */
    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (BobException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /** Runs the interactive task manager. */
    public void run() {
        ui.showWelcome();
        while (true) {
            String command = ui.readCommand();
            try {
                CommandType type = parser.parse(command);
                if (type == CommandType.BYE) {
                    ui.show("Bye. Hope to see you again soon!");
                    break;
                }
                tasks.execute(command, type, ui, storage);
            } catch (BobException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid task number.");
            }
        }
        ui.close();
    }

    /** Starts Bob with the default task file. */
    public static void main(String[] args) {
        new Bob("data/duke.txt").run();
    }
}
