
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

    public boolean getDone(){
        return this.done;
    }

    public String getItem(){
        return this.item;
    }

    @Override
    public String toString() {
        return "[T][" + (done ? "X" : " ") + "] " + item;
    }
}