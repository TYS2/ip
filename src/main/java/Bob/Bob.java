package bob;

/** Coordinates the task manager components. */
public class Bob {
    private Storage storage;
    private TaskList tasks;
    private Parser parser;
    private String initializationError;

    /**
     * Creates Bob using the supplied task data file.
     *
     * @param filePath Path of the task data file.
     */
    public Bob(String filePath) {
        storage = new bob.Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (BobException e) {
            initializationError = e.getMessage();
            tasks = new TaskList();
        }
    }

    /** Handles one command and returns the complete response for the caller to display. */
    public String getResponse(String command) {
        if (initializationError != null) {
            return "OOPS!!! " + initializationError;
        }
        try {
            CommandType type = parser.parse(command);
            if (type == CommandType.BYE) {
                return "Bye. Hope to see you again soon!";
            } else {
                return tasks.execute(command, type, storage);
            }
        } catch (BobException e) {
            return "OOPS!!! " + e.getMessage();
        } catch (NumberFormatException e) {
            return "OOPS!!! Please enter a valid task number.";
        }
    }
}
