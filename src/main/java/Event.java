public class Event extends Task {
    private String from;
    private String to;

    public Event(String item, String from, String to) {
        super(item);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E][" + (getDone() ? "X" : " ") + "] "
                + getItem() + " (from: " + from + " to: " + to + ")";
    }
}