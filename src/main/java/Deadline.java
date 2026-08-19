public class Deadline extends Task{
    private String end;

    public Deadline(String item, String end){
        super(item);
        this.end=end;
    }

    @Override
    public String toString(){
        return "[" + (super.getDone() ? "X" : " ") + "] " + super.getItem();
    }
}

