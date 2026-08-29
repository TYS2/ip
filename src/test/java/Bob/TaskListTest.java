package bob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    @DisplayName("Adding a todo increases the task count and stores its description")
    public void addTodo_validDescription_taskIsAdded() throws BobException {
        TaskList taskList = new TaskList();

        Task task = taskList.addTodo("Read a book");

        assertEquals(1, taskList.size());
        assertEquals("Read a book", task.getItem());
        assertEquals(task, taskList.get(0));
    }

    @Test
    @DisplayName("Adding an empty todo throws an exception")
    public void addTodo_emptyDescription_exceptionThrown() {
        TaskList taskList = new TaskList();

        assertThrows(BobException.class, () -> taskList.addTodo(""));
        assertEquals(0, taskList.size());
    }

    @Test
    @DisplayName("Deleting a task removes the requested one")
    public void deleteTask_validTaskNumber_taskRemoved() throws BobException {
        TaskList taskList = new TaskList();
        taskList.addTodo("First task");
        taskList.addTodo("Second task");

        Task deleted = taskList.deleteTask(1);

        assertEquals("First task", deleted.getItem());
        assertEquals(1, taskList.size());
        assertEquals("Second task", taskList.get(0).getItem());
    }

    @Test
    @DisplayName("Deleting an invalid task number throws an exception")
    public void deleteTask_invalidTaskNumber_exceptionThrown() throws BobException {
        TaskList taskList = new TaskList();
        taskList.addTodo("Only task");

        assertThrows(BobException.class, () -> taskList.deleteTask(2));
        assertEquals(1, taskList.size());
    }
}
