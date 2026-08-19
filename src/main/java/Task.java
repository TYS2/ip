
public class Task {
    private String item;
    private boolean done;

    public Task(String item) {
        this.item = item;
        this.done = false;
    }

    public void markDone() {
        this.done = true;
    }

    public void markUndone() {
        this.done = false;
    }

    @Override
    public String toString() {
        return "[" + (done ? "X" : " ") + "] " + item;
    }
}