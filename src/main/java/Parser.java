/** Converts the first word of a user command into a command type. */
public class Parser {

    /**
     * Determines which command the user entered from its first word.
     *
     * @param command the complete command entered by the user
     * @return the command type represented by the command keyword
     */
    public CommandType parse(String command) {
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
}
