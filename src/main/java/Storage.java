import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Loads and saves tasks from the application's data file. */
public class Storage {
    private final Path file;

    /** Creates storage backed by the supplied file path. */
    public Storage(String filePath) {
        file = Path.of(filePath);
    }

    /**
     * Loads valid tasks from the data file.
     *
     * @return the tasks found in the data file, or an empty list if it does
     *         not exist.
     * @throws BobException if the file cannot be read.
     */
    public ArrayList<Task> load() throws BobException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(file)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(file)) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (NoSuchFileException e) {
            // The file may have been removed after the existence check.
            return tasks;
        } catch (IOException e) {
            throw new BobException("I couldn't load your tasks.");
        }

        return tasks;
    }

    /**
     * Converts one saved line into a task.
     *
     * @param line a line from the task data file.
     * @return the parsed task, or null when the line is malformed.
     */
    private static Task parseTask(String line) {
        String[] parts = line.split("\\s*\\|\\s*", 5);
        if (parts.length < 3
                || (!parts[1].equals("0") && !parts[1].equals("1"))) {
            return null;
        }

        String type = parts[0];
        String description = parts[2].trim();
        if (description.isEmpty()) {
            return null;
        }

        Task task;
        switch (type) {
            case "T":
                task = new Task(description);
                break;
            case "D":
                if (parts.length < 4 || parts[3].trim().isEmpty()) {
                    return null;
                }

                try {
                    task = new Deadline(
                            description,
                            LocalDate.parse(parts[3].trim()));
                } catch (DateTimeParseException e) {
                    return null;
                }
                break;

            case "E":
                if (parts.length < 5 || parts[3].trim().isEmpty()
                        || parts[4].trim().isEmpty()) {
                    return null;
                }

                try {
                    task = new Event(
                            description,
                            LocalDate.parse(parts[3].trim()),
                            LocalDate.parse(parts[4].trim()));
                } catch (DateTimeParseException e) {
                    return null;
                }
                break;
            default:
                return null;
        }

        if (parts[1].equals("1")) {
            task.markDone();
        }
        return task;
    }

    /**
     * Replaces the data file with the current task list.
     *
     * @param tasks tasks to save.
     * @throws BobException if the directory or file cannot be written.
     */
    public void save(ArrayList<Task> tasks) throws BobException {
        ArrayList<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.toStorageString());
        }

        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            Files.write(file, lines);
        } catch (IOException e) {
            throw new BobException("I couldn't save your tasks.");
        }
    }
}
