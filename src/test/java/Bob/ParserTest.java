package bob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParserTest {
    @DisplayName("Test for parsing command")
    @Test
    public void parse_bye_returnsBye() {
        Parser parser = new Parser();
        assertEquals(CommandType.BYE, parser.parse("bye"));
    }

    @DisplayName("Test for parsing the list command")
    @Test
    public void testCheckForListCommand() {
        Parser parser = new Parser();
        assertEquals(CommandType.LIST, parser.parse("list"));
    }

    @DisplayName("Test for creating a deadline task")
    @Test
    public void testAddDeadlineTask() throws BobException {
        TaskList taskList = new TaskList();

        Task deadline = taskList.addDeadline("Submit report /by 2026-09-01");

        assertEquals(1, taskList.size());
        assertEquals(
                "[D][ ] Submit report (by: Sept 01 2026)",
                deadline.toString());
    }
}
